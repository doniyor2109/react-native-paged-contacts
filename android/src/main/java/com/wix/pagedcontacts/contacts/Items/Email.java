package com.wix.pagedcontacts.contacts.Items;

import android.database.Cursor;
import android.provider.ContactsContract;

import com.facebook.react.bridge.WritableMap;
import com.wix.pagedcontacts.contacts.Field;
import com.wix.pagedcontacts.contacts.query.QueryParams;

class Email extends ContactItem {
    private String label;
    private String address;

    Email(Cursor cursor) {
        super(cursor);
        fillFromCursor();
    }

    private void fillFromCursor() {
        address = getString(ContactsContract.CommonDataKinds.Email.ADDRESS);
        final String label = getString(ContactsContract.CommonDataKinds.Email.LABEL);
        final Integer typeId = getInt(ContactsContract.CommonDataKinds.Email.TYPE);
        this.label = getEmailType(typeId, label);
    }

    private String getEmailType(Integer type, String label) {
        if (type == null) {
            throw new InvalidCursorTypeException();
        }
        switch (type) {
            case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                return "home";
            case ContactsContract.CommonDataKinds.Email.TYPE_MOBILE:
                return "mobile";
            case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                return "work";
            case ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM:
                return label;
            default:
                return "other";
        }
    }

    @Override
    protected void fillMap(WritableMap map, QueryParams params) {
        if (params.fetchField(Field.emailAddresses)) {
            addToMap(map, "label", label);
            addToMap(map, "value", address);
        }
    }
}
