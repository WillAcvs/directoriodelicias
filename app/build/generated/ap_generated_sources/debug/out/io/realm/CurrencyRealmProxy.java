package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsList;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.Property;
import io.realm.internal.ProxyUtils;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("all")
public class CurrencyRealmProxy extends com.directoriodelicias.apps.delicias.classes.Currency
    implements RealmObjectProxy, CurrencyRealmProxyInterface {

    static final class CurrencyColumnInfo extends ColumnInfo {
        long idIndex;
        long statusIndex;
        long codeIndex;
        long symbolIndex;
        long nameIndex;
        long formatIndex;
        long rateIndex;

        CurrencyColumnInfo(OsSchemaInfo schemaInfo) {
            super(7);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("Currency");
            this.idIndex = addColumnDetails("id", objectSchemaInfo);
            this.statusIndex = addColumnDetails("status", objectSchemaInfo);
            this.codeIndex = addColumnDetails("code", objectSchemaInfo);
            this.symbolIndex = addColumnDetails("symbol", objectSchemaInfo);
            this.nameIndex = addColumnDetails("name", objectSchemaInfo);
            this.formatIndex = addColumnDetails("format", objectSchemaInfo);
            this.rateIndex = addColumnDetails("rate", objectSchemaInfo);
        }

        CurrencyColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new CurrencyColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final CurrencyColumnInfo src = (CurrencyColumnInfo) rawSrc;
            final CurrencyColumnInfo dst = (CurrencyColumnInfo) rawDst;
            dst.idIndex = src.idIndex;
            dst.statusIndex = src.statusIndex;
            dst.codeIndex = src.codeIndex;
            dst.symbolIndex = src.symbolIndex;
            dst.nameIndex = src.nameIndex;
            dst.formatIndex = src.formatIndex;
            dst.rateIndex = src.rateIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(7);
        fieldNames.add("id");
        fieldNames.add("status");
        fieldNames.add("code");
        fieldNames.add("symbol");
        fieldNames.add("name");
        fieldNames.add("format");
        fieldNames.add("rate");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private CurrencyColumnInfo columnInfo;
    private ProxyState<com.directoriodelicias.apps.delicias.classes.Currency> proxyState;

    CurrencyRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (CurrencyColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.directoriodelicias.apps.delicias.classes.Currency>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.idIndex);
    }

    @Override
    public void realmSet$id(int value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'id' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$status() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.statusIndex);
    }

    @Override
    public void realmSet$status(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.statusIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.statusIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$code() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.codeIndex);
    }

    @Override
    public void realmSet$code(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.codeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.codeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.codeIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.codeIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$symbol() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.symbolIndex);
    }

    @Override
    public void realmSet$symbol(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.symbolIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.symbolIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.symbolIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.symbolIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.nameIndex);
    }

    @Override
    public void realmSet$name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.nameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.nameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.nameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.nameIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$format() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.formatIndex);
    }

    @Override
    public void realmSet$format(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.formatIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.formatIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$rate() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.rateIndex);
    }

    @Override
    public void realmSet$rate(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.rateIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.rateIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("Currency", 7, 0);
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("status", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("code", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("symbol", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("format", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("rate", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static CurrencyColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new CurrencyColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "Currency";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.directoriodelicias.apps.delicias.classes.Currency createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.directoriodelicias.apps.delicias.classes.Currency obj = null;
        if (update) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Currency.class);
            CurrencyColumnInfo columnInfo = (CurrencyColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Currency.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Currency.class), false, Collections.<String> emptyList());
                    obj = new io.realm.CurrencyRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.CurrencyRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Currency.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.CurrencyRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Currency.class, json.getInt("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }

        final CurrencyRealmProxyInterface objProxy = (CurrencyRealmProxyInterface) obj;
        if (json.has("status")) {
            if (json.isNull("status")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'status' to null.");
            } else {
                objProxy.realmSet$status((int) json.getInt("status"));
            }
        }
        if (json.has("code")) {
            if (json.isNull("code")) {
                objProxy.realmSet$code(null);
            } else {
                objProxy.realmSet$code((String) json.getString("code"));
            }
        }
        if (json.has("symbol")) {
            if (json.isNull("symbol")) {
                objProxy.realmSet$symbol(null);
            } else {
                objProxy.realmSet$symbol((String) json.getString("symbol"));
            }
        }
        if (json.has("name")) {
            if (json.isNull("name")) {
                objProxy.realmSet$name(null);
            } else {
                objProxy.realmSet$name((String) json.getString("name"));
            }
        }
        if (json.has("format")) {
            if (json.isNull("format")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'format' to null.");
            } else {
                objProxy.realmSet$format((int) json.getInt("format"));
            }
        }
        if (json.has("rate")) {
            if (json.isNull("rate")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'rate' to null.");
            } else {
                objProxy.realmSet$rate((int) json.getInt("rate"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.directoriodelicias.apps.delicias.classes.Currency createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.directoriodelicias.apps.delicias.classes.Currency obj = new com.directoriodelicias.apps.delicias.classes.Currency();
        final CurrencyRealmProxyInterface objProxy = (CurrencyRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'id' to null.");
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("status")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$status((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'status' to null.");
                }
            } else if (name.equals("code")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$code((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$code(null);
                }
            } else if (name.equals("symbol")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$symbol((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$symbol(null);
                }
            } else if (name.equals("name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$name(null);
                }
            } else if (name.equals("format")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$format((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'format' to null.");
                }
            } else if (name.equals("rate")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$rate((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'rate' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
        }
        return realm.copyToRealm(obj);
    }

    public static com.directoriodelicias.apps.delicias.classes.Currency copyOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Currency object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null) {
            final BaseRealm otherRealm = ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm();
            if (otherRealm.threadId != realm.threadId) {
                throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
            }
            if (otherRealm.getPath().equals(realm.getPath())) {
                return object;
            }
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.directoriodelicias.apps.delicias.classes.Currency) cachedRealmObject;
        }

        com.directoriodelicias.apps.delicias.classes.Currency realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Currency.class);
            CurrencyColumnInfo columnInfo = (CurrencyColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Currency.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((CurrencyRealmProxyInterface) object).realmGet$id());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Currency.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.CurrencyRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.directoriodelicias.apps.delicias.classes.Currency copy(Realm realm, com.directoriodelicias.apps.delicias.classes.Currency newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.directoriodelicias.apps.delicias.classes.Currency) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.directoriodelicias.apps.delicias.classes.Currency realmObject = realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Currency.class, ((CurrencyRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        CurrencyRealmProxyInterface realmObjectSource = (CurrencyRealmProxyInterface) newObject;
        CurrencyRealmProxyInterface realmObjectCopy = (CurrencyRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$status(realmObjectSource.realmGet$status());
        realmObjectCopy.realmSet$code(realmObjectSource.realmGet$code());
        realmObjectCopy.realmSet$symbol(realmObjectSource.realmGet$symbol());
        realmObjectCopy.realmSet$name(realmObjectSource.realmGet$name());
        realmObjectCopy.realmSet$format(realmObjectSource.realmGet$format());
        realmObjectCopy.realmSet$rate(realmObjectSource.realmGet$rate());
        return realmObject;
    }

    public static long insert(Realm realm, com.directoriodelicias.apps.delicias.classes.Currency object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Currency.class);
        long tableNativePtr = table.getNativePtr();
        CurrencyColumnInfo columnInfo = (CurrencyColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Currency.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((CurrencyRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((CurrencyRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((CurrencyRealmProxyInterface) object).realmGet$id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((CurrencyRealmProxyInterface) object).realmGet$status(), false);
        String realmGet$code = ((CurrencyRealmProxyInterface) object).realmGet$code();
        if (realmGet$code != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.codeIndex, rowIndex, realmGet$code, false);
        }
        String realmGet$symbol = ((CurrencyRealmProxyInterface) object).realmGet$symbol();
        if (realmGet$symbol != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.symbolIndex, rowIndex, realmGet$symbol, false);
        }
        String realmGet$name = ((CurrencyRealmProxyInterface) object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.formatIndex, rowIndex, ((CurrencyRealmProxyInterface) object).realmGet$format(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.rateIndex, rowIndex, ((CurrencyRealmProxyInterface) object).realmGet$rate(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Currency.class);
        long tableNativePtr = table.getNativePtr();
        CurrencyColumnInfo columnInfo = (CurrencyColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Currency.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.Currency object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Currency) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((CurrencyRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((CurrencyRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((CurrencyRealmProxyInterface) object).realmGet$id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((CurrencyRealmProxyInterface) object).realmGet$status(), false);
            String realmGet$code = ((CurrencyRealmProxyInterface) object).realmGet$code();
            if (realmGet$code != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.codeIndex, rowIndex, realmGet$code, false);
            }
            String realmGet$symbol = ((CurrencyRealmProxyInterface) object).realmGet$symbol();
            if (realmGet$symbol != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.symbolIndex, rowIndex, realmGet$symbol, false);
            }
            String realmGet$name = ((CurrencyRealmProxyInterface) object).realmGet$name();
            if (realmGet$name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.formatIndex, rowIndex, ((CurrencyRealmProxyInterface) object).realmGet$format(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.rateIndex, rowIndex, ((CurrencyRealmProxyInterface) object).realmGet$rate(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Currency object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Currency.class);
        long tableNativePtr = table.getNativePtr();
        CurrencyColumnInfo columnInfo = (CurrencyColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Currency.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((CurrencyRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((CurrencyRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((CurrencyRealmProxyInterface) object).realmGet$id());
        }
        cache.put(object, rowIndex);
        Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((CurrencyRealmProxyInterface) object).realmGet$status(), false);
        String realmGet$code = ((CurrencyRealmProxyInterface) object).realmGet$code();
        if (realmGet$code != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.codeIndex, rowIndex, realmGet$code, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.codeIndex, rowIndex, false);
        }
        String realmGet$symbol = ((CurrencyRealmProxyInterface) object).realmGet$symbol();
        if (realmGet$symbol != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.symbolIndex, rowIndex, realmGet$symbol, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.symbolIndex, rowIndex, false);
        }
        String realmGet$name = ((CurrencyRealmProxyInterface) object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.formatIndex, rowIndex, ((CurrencyRealmProxyInterface) object).realmGet$format(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.rateIndex, rowIndex, ((CurrencyRealmProxyInterface) object).realmGet$rate(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Currency.class);
        long tableNativePtr = table.getNativePtr();
        CurrencyColumnInfo columnInfo = (CurrencyColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Currency.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.Currency object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Currency) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((CurrencyRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((CurrencyRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((CurrencyRealmProxyInterface) object).realmGet$id());
            }
            cache.put(object, rowIndex);
            Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((CurrencyRealmProxyInterface) object).realmGet$status(), false);
            String realmGet$code = ((CurrencyRealmProxyInterface) object).realmGet$code();
            if (realmGet$code != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.codeIndex, rowIndex, realmGet$code, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.codeIndex, rowIndex, false);
            }
            String realmGet$symbol = ((CurrencyRealmProxyInterface) object).realmGet$symbol();
            if (realmGet$symbol != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.symbolIndex, rowIndex, realmGet$symbol, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.symbolIndex, rowIndex, false);
            }
            String realmGet$name = ((CurrencyRealmProxyInterface) object).realmGet$name();
            if (realmGet$name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.formatIndex, rowIndex, ((CurrencyRealmProxyInterface) object).realmGet$format(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.rateIndex, rowIndex, ((CurrencyRealmProxyInterface) object).realmGet$rate(), false);
        }
    }

    public static com.directoriodelicias.apps.delicias.classes.Currency createDetachedCopy(com.directoriodelicias.apps.delicias.classes.Currency realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.directoriodelicias.apps.delicias.classes.Currency unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.directoriodelicias.apps.delicias.classes.Currency();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.directoriodelicias.apps.delicias.classes.Currency) cachedObject.object;
            }
            unmanagedObject = (com.directoriodelicias.apps.delicias.classes.Currency) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        CurrencyRealmProxyInterface unmanagedCopy = (CurrencyRealmProxyInterface) unmanagedObject;
        CurrencyRealmProxyInterface realmSource = (CurrencyRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());
        unmanagedCopy.realmSet$status(realmSource.realmGet$status());
        unmanagedCopy.realmSet$code(realmSource.realmGet$code());
        unmanagedCopy.realmSet$symbol(realmSource.realmGet$symbol());
        unmanagedCopy.realmSet$name(realmSource.realmGet$name());
        unmanagedCopy.realmSet$format(realmSource.realmGet$format());
        unmanagedCopy.realmSet$rate(realmSource.realmGet$rate());

        return unmanagedObject;
    }

    static com.directoriodelicias.apps.delicias.classes.Currency update(Realm realm, com.directoriodelicias.apps.delicias.classes.Currency realmObject, com.directoriodelicias.apps.delicias.classes.Currency newObject, Map<RealmModel, RealmObjectProxy> cache) {
        CurrencyRealmProxyInterface realmObjectTarget = (CurrencyRealmProxyInterface) realmObject;
        CurrencyRealmProxyInterface realmObjectSource = (CurrencyRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$status(realmObjectSource.realmGet$status());
        realmObjectTarget.realmSet$code(realmObjectSource.realmGet$code());
        realmObjectTarget.realmSet$symbol(realmObjectSource.realmGet$symbol());
        realmObjectTarget.realmSet$name(realmObjectSource.realmGet$name());
        realmObjectTarget.realmSet$format(realmObjectSource.realmGet$format());
        realmObjectTarget.realmSet$rate(realmObjectSource.realmGet$rate());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Currency = proxy[");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{status:");
        stringBuilder.append(realmGet$status());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{code:");
        stringBuilder.append(realmGet$code() != null ? realmGet$code() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{symbol:");
        stringBuilder.append(realmGet$symbol() != null ? realmGet$symbol() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{name:");
        stringBuilder.append(realmGet$name() != null ? realmGet$name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{format:");
        stringBuilder.append(realmGet$format());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{rate:");
        stringBuilder.append(realmGet$rate());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyRealmProxy aCurrency = (CurrencyRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aCurrency.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aCurrency.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aCurrency.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
