package domain.builders;

import domain.Transaction;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe responsável pela criação de builders de entidades
 *
 * @author wcaquino
 *
 */
public class BuilderMasterPojo {

    Set<String> importsList;

    public BuilderMasterPojo() {
        importsList = new HashSet<String>();
        importsList.add("import java.util.Arrays;");
//		listaImports.add("import java.util.Collections;");
//		listaImports.add("import java.util.ArrayList;");
    }

    @SuppressWarnings("rawtypes")
    public void generateClassCode(Class clazz) {


        String className = clazz.getSimpleName() + "Builder";

        StringBuilder builder = new StringBuilder();

        builder.append("public class ").append(className).append(" {\n");
        builder.append("\tprivate ").append(clazz.getSimpleName()).append(" element;\n");

        builder.append("\tprivate ").append(className).append("(){}\n\n");

        builder.append("\tpublic static ").append(className).append(" one").append(clazz.getSimpleName()).append("() {\n");
        builder.append("\t\t").append(className).append(" builder = new ").append(className).append("();\n");
        builder.append("\t\tloadDefaultData(builder);\n");
        builder.append("\t\treturn builder;\n");
        builder.append("\t}\n\n");

        builder.append("\tpublic static void loadDefaultData(").append(className).append(" builder) {\n");
        builder.append("\t\tbuilder.element = new ").append(clazz.getSimpleName()).append("();\n");
        builder.append("\t\t").append(clazz.getSimpleName()).append(" element = builder.element;\n");
        builder.append("\n\t\t\n");

        List<Field> declaredFields = getClassFields(clazz);
        for(Field campo: declaredFields) {
            if(campo.getName().equals("serialVersionUID"))
                continue;
            if(Modifier.isStatic(campo.getModifiers()))
                continue;
            builder.append("\t\telement.set").append(campo.getName().substring(0, 1).toUpperCase()).append(campo.getName().substring(1)).append("(").append(getDefaultParameter(campo)).append(");\n");

        }
        builder.append("\t}\n\n");

        for(Field campo: declaredFields) {
            if(campo.getName().equals("serialVersionUID"))
                continue;
            if(Modifier.isStatic(campo.getModifiers()))
                continue;
            if(campo.getType().getSimpleName().equals("List")) {
                ParameterizedType stringListType = (ParameterizedType) campo.getGenericType();
                builder.append("\tpublic ")
                        .append(className)
                        .append(" withList").append(campo.getName().substring(0, 1).toUpperCase()).append(campo.getName().substring(1))
                        .append("(").append(((Class)stringListType.getActualTypeArguments()[0]).getSimpleName()).append("... params) {\n");
////				List<elemType> lista = new ArrayList<elemType>();
//				builder.append("\t\tList<").append(((Class)stringListType.getActualTypeArguments()[0]).getSimpleName()).append("> lista = new ArrayList<")
//					.append(((Class)stringListType.getActualTypeArguments()[0]).getSimpleName()).append(">();\n");
//				registrarImports(((Class)stringListType.getActualTypeArguments()[0]).getName());
////				Collections.addAll(lista, args);
//				builder.append("\t\tCollections.addAll(lista, params);\n");
////				elemento.setelemTypes(lista);
                builder.append("\t\telement.set").append(campo.getName().substring(0, 1).toUpperCase()).append(campo.getName().substring(1)).append("(Arrays.asList(params));\n");

                builder.append("\t\treturn this;\n");
                builder.append("\t}\n\n");
            } else {
                builder.append("\tpublic ")
                        .append(className)
                        .append(" with").append(campo.getName().substring(0, 1).toUpperCase()).append(campo.getName().substring(1))
                        .append("(").append(campo.getType().getSimpleName()).append(" param) {\n");
                registrarImports(campo.getType().getCanonicalName());
                builder.append("\t\telement.set")
                        .append(campo.getName().substring(0, 1).toUpperCase()).append(campo.getName().substring(1))
                        .append("(param);\n");
                builder.append("\t\treturn this;\n");
                builder.append("\t}\n\n");
            }
        }

        builder.append("\tpublic ").append(clazz.getSimpleName()).append(" readyToUse() {\n");
        builder.append("\t\treturn element;\n");
        builder.append("\t}\n");

        builder.append("}");

        for(String str: importsList) {
            System.out.println(str);
        }
        System.out.println("import " + clazz.getCanonicalName() + ";");
        System.out.println("\n");
        System.out.println(builder.toString());
    }

    @SuppressWarnings("rawtypes")
    public List<Field> getClassFields(Class classe) {
        List<Field> fields = new ArrayList<Field>();
        fields.addAll(Arrays.asList(classe.getDeclaredFields()));
        Class superClass = classe.getSuperclass();
        if(superClass != Object.class) {
            List<Field> fieldsSC = Arrays.asList(superClass.getDeclaredFields());
            fields.addAll(fieldsSC);
        }
        return fields;
    }

    public String getDefaultParameter(Field campo) {
        String tipo = campo.getType().getSimpleName();
        if(tipo.equals("int") || tipo.equals("Integer")){
            return "0";
        }
        if(tipo.equals("long") || tipo.equals("Long")){
            return "0L";
        }
        if(tipo.equals("double") || tipo.equals("Double")){
            return "0.0";
        }
        if(tipo.equals("boolean") || tipo.equals("Boolean")){
            return "false";
        }
        if(tipo.equals("String")){
            return "\"\"";
        }
        return "null";
    }

    public void registrarImports(String classe) {
        if(classe.contains("."))
            importsList.add("import " + classe + ";");
    }

    public static void main(String[] args) {
        new BuilderMasterPojo().generateClassCode(Transaction.class);
    }

}
