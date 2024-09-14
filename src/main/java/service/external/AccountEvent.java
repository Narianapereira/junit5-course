package service.external;

import domain.Account;

public interface AccountEvent {
    public enum EventType {
        CREATED, UPDATED, DELETED
    }

    void dispatch(Account account, EventType eventType);
}
