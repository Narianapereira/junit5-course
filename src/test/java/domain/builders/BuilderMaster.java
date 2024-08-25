package domain.builders;

import domain.User;

import static java.lang.String.format;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Classe responsável pela criação de builders de entidades
 *
 * @author wcaquino@gmail.com
 */
public class BuilderMaster {

    Set<String> importsList = new HashSet<String>();

    @SuppressWarnings("rawtypes")
    public void generateClassCode(Class clazz) {

        String nameClass = clazz.getSimpleName() + "Builder";

        StringBuilder builder = new StringBuilder();

        builder.append(format("public class %s {\n", nameClass));
        List<Field> declaredFields = getClassFields(clazz).stream()
                .filter(field -> !field.getName().equals("serialVersionUID") && !Modifier.isStatic(field.getModifiers()))
                .collect(Collectors.toList());
        declaredFields.forEach(field -> {
            if (field.getType().getSimpleName().equals("List"))
                builder.append(format("\tprivate %s<%s> %s;\n", field.getType().getSimpleName(), getGenericSimpleName(field), field.getName()));
            else
                builder.append(format("\tprivate %s %s;\n", field.getType().getSimpleName(), field.getName()));
        });

        builder.append(format("\n\tprivate %s(){}\n\n", nameClass));

        builder.append(format("\tpublic static %s one%s() {\n", nameClass, clazz.getSimpleName()));
        builder.append(format("\t\t%s builder = new %s();\n", nameClass, nameClass));
        builder.append("\t\tloadDefaultData(builder);\n");
        builder.append("\t\treturn builder;\n");
        builder.append("\t}\n\n");

        builder.append(format("\tprivate static void loadDefaultData(%s builder) {\n", nameClass));
        declaredFields.forEach(campo -> builder.append(format("\t\tbuilder.%s = %s;\n", campo.getName(), getDefaultParameter(campo))));
        builder.append("\t}\n\n");

        for (Field field : declaredFields) {
            registerImports(field.getType().getCanonicalName());
            if (field.getType().getSimpleName().equals("List")) {
                registerImports("java.util.Arrays");
                builder.append(format("\tpublic %s withList%s%s(%s... %s) {\n",
                        nameClass, field.getName().substring(0, 1).toUpperCase(), field.getName().substring(1), getGenericSimpleName(field), field.getName()));
                builder.append(format("\t\tthis.%s = Arrays.asList(%s);\n", field.getName(), field.getName()));
            } else {
                builder.append(format("\tpublic %s with%s%s(%s %s) {\n",
                        nameClass, field.getName().substring(0, 1).toUpperCase(), field.getName().substring(1), field.getType().getSimpleName(), field.getName()));
                builder.append(format("\t\tthis.%s = %s;\n", field.getName(), field.getName()));
            }
            builder.append("\t\treturn this;\n");
            builder.append("\t}\n\n");
        }

        builder.append(format("\tpublic %s readyToUse() {\n", clazz.getSimpleName()));
        builder.append(format("\t\treturn new %s(", clazz.getSimpleName()));
        boolean first = true;
        for (Field field : declaredFields) {
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }
            builder.append(field.getName());
        }
        builder.append(");\n\t}\n");

        builder.append("}");

        for (String str : importsList) {
            if (!str.contains("java.lang."))
                System.out.println(str);
        }
        System.out.println(format("import %s;\n", clazz.getCanonicalName()));
        System.out.println(builder.toString());
    }

    @SuppressWarnings("rawtypes")
    private String getGenericSimpleName(Field field) {
        ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
        return ((Class) stringListType.getActualTypeArguments()[0]).getSimpleName();
    }

    @SuppressWarnings("rawtypes")
    public List<Field> getClassFields(Class classe) {
        List<Field> fields = new ArrayList<Field>();
        fields.addAll(Arrays.asList(classe.getDeclaredFields()));
        Class superClass = classe.getSuperclass();
        if (superClass != Object.class) {
            List<Field> fieldsSC = Arrays.asList(superClass.getDeclaredFields());
            fields.addAll(fieldsSC);
        }
        return fields;
    }

    public String getDefaultParameter(Field campo) {
        String type = campo.getType().getSimpleName();
        if (type.equals("int") || type.equals("Integer")) {
            return "0";
        }
        if (type.equalsIgnoreCase("long")) {
            return "0L";
        }
        if (type.equalsIgnoreCase("double") || type.equalsIgnoreCase("float")) {
            return "0.0";
        }
        if (type.equalsIgnoreCase("boolean")) {
            return "false";
        }
        if (type.equals("String")) {
            return "\"\"";
        }
        return "null";
    }

    public void registerImports(String classe) {
        if (classe.contains("."))
            importsList.add("import " + classe + ";");
    }

    public static void main(String[] args) {
        BuilderMaster master = new BuilderMaster();
        master.generateClassCode(User.class);
    }
}
