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
public class OpeningTimeRealmProxy extends com.directoriodelicias.apps.delicias.classes.OpeningTime
    implements RealmObjectProxy, OpeningTimeRealmProxyInterface {

    static final class OpeningTimeColumnInfo extends ColumnInfo {
        long idIndex;
        long store_idIndex;
        long dayIndex;
        long openingIndex;
        long closingIndex;
        long enabledIndex;
        long timezoneIndex;

        OpeningTimeColumnInfo(OsSchemaInfo schemaInfo) {
            super(7);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("OpeningTime");
            this.idIndex = addColumnDetails("id", objectSchemaInfo);
            this.store_idIndex = addColumnDetails("store_id", objectSchemaInfo);
            this.dayIndex = addColumnDetails("day", objectSchemaInfo);
            this.openingIndex = addColumnDetails("opening", objectSchemaInfo);
            this.closingIndex = addColumnDetails("closing", objectSchemaInfo);
            this.enabledIndex = addColumnDetails("enabled", objectSchemaInfo);
            this.timezoneIndex = addColumnDetails("timezone", objectSchemaInfo);
        }

        OpeningTimeColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new OpeningTimeColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final OpeningTimeColumnInfo src = (OpeningTimeColumnInfo) rawSrc;
            final OpeningTimeColumnInfo dst = (OpeningTimeColumnInfo) rawDst;
            dst.idIndex = src.idIndex;
            dst.store_idIndex = src.store_idIndex;
            dst.dayIndex = src.dayIndex;
            dst.openingIndex = src.openingIndex;
            dst.closingIndex = src.closingIndex;
            dst.enabledIndex = src.enabledIndex;
            dst.timezoneIndex = src.timezoneIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(7);
        fieldNames.add("id");
        fieldNames.add("store_id");
        fieldNames.add("day");
        fieldNames.add("opening");
        fieldNames.add("closing");
        fieldNames.add("enabled");
        fieldNames.add("timezone");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private OpeningTimeColumnInfo columnInfo;
    private ProxyState<com.directoriodelicias.apps.delicias.classes.OpeningTime> proxyState;

    OpeningTimeRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (OpeningTimeColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.directoriodelicias.apps.delicias.classes.OpeningTime>(this);
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
    public int realmGet$store_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.store_idIndex);
    }

    @Override
    public void realmSet$store_id(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.store_idIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.store_idIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$day() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.dayIndex);
    }

    @Override
    public void realmSet$day(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.dayIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.dayIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.dayIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.dayIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$opening() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.openingIndex);
    }

    @Override
    public void realmSet$opening(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.openingIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.openingIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.openingIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.openingIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$closing() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.closingIndex);
    }

    @Override
    public void realmSet$closing(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.closingIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.closingIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.closingIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.closingIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$enabled() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.enabledIndex);
    }

    @Override
    public void realmSet$enabled(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.enabledIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.enabledIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$timezone() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.timezoneIndex);
    }

    @Override
    public void realmSet$timezone(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.timezoneIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.timezoneIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.timezoneIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.timezoneIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("OpeningTime", 7, 0);
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("store_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("day", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("opening", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("closing", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("enabled", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("timezone", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static OpeningTimeColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new OpeningTimeColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "OpeningTime";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.directoriodelicias.apps.delicias.classes.OpeningTime createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.directoriodelicias.apps.delicias.classes.OpeningTime obj = null;
        if (update) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.OpeningTime.class);
            OpeningTimeColumnInfo columnInfo = (OpeningTimeColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.OpeningTime.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.OpeningTime.class), false, Collections.<String> emptyList());
                    obj = new io.realm.OpeningTimeRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.OpeningTimeRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.OpeningTime.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.OpeningTimeRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.OpeningTime.class, json.getInt("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }

        final OpeningTimeRealmProxyInterface objProxy = (OpeningTimeRealmProxyInterface) obj;
        if (json.has("store_id")) {
            if (json.isNull("store_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'store_id' to null.");
            } else {
                objProxy.realmSet$store_id((int) json.getInt("store_id"));
            }
        }
        if (json.has("day")) {
            if (json.isNull("day")) {
                objProxy.realmSet$day(null);
            } else {
                objProxy.realmSet$day((String) json.getString("day"));
            }
        }
        if (json.has("opening")) {
            if (json.isNull("opening")) {
                objProxy.realmSet$opening(null);
            } else {
                objProxy.realmSet$opening((String) json.getString("opening"));
            }
        }
        if (json.has("closing")) {
            if (json.isNull("closing")) {
                objProxy.realmSet$closing(null);
            } else {
                objProxy.realmSet$closing((String) json.getString("closing"));
            }
        }
        if (json.has("enabled")) {
            if (json.isNull("enabled")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'enabled' to null.");
            } else {
                objProxy.realmSet$enabled((int) json.getInt("enabled"));
            }
        }
        if (json.has("timezone")) {
            if (json.isNull("timezone")) {
                objProxy.realmSet$timezone(null);
            } else {
                objProxy.realmSet$timezone((String) json.getString("timezone"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.directoriodelicias.apps.delicias.classes.OpeningTime createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.directoriodelicias.apps.delicias.classes.OpeningTime obj = new com.directoriodelicias.apps.delicias.classes.OpeningTime();
        final OpeningTimeRealmProxyInterface objProxy = (OpeningTimeRealmProxyInterface) obj;
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
            } else if (name.equals("store_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$store_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'store_id' to null.");
                }
            } else if (name.equals("day")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$day((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$day(null);
                }
            } else if (name.equals("opening")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$opening((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$opening(null);
                }
            } else if (name.equals("closing")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$closing((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$closing(null);
                }
            } else if (name.equals("enabled")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$enabled((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'enabled' to null.");
                }
            } else if (name.equals("timezone")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$timezone((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$timezone(null);
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

    public static com.directoriodelicias.apps.delicias.classes.OpeningTime copyOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.OpeningTime object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.directoriodelicias.apps.delicias.classes.OpeningTime) cachedRealmObject;
        }

        com.directoriodelicias.apps.delicias.classes.OpeningTime realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.OpeningTime.class);
            OpeningTimeColumnInfo columnInfo = (OpeningTimeColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.OpeningTime.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((OpeningTimeRealmProxyInterface) object).realmGet$id());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.OpeningTime.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.OpeningTimeRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.directoriodelicias.apps.delicias.classes.OpeningTime copy(Realm realm, com.directoriodelicias.apps.delicias.classes.OpeningTime newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.directoriodelicias.apps.delicias.classes.OpeningTime) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.directoriodelicias.apps.delicias.classes.OpeningTime realmObject = realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.OpeningTime.class, ((OpeningTimeRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        OpeningTimeRealmProxyInterface realmObjectSource = (OpeningTimeRealmProxyInterface) newObject;
        OpeningTimeRealmProxyInterface realmObjectCopy = (OpeningTimeRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$store_id(realmObjectSource.realmGet$store_id());
        realmObjectCopy.realmSet$day(realmObjectSource.realmGet$day());
        realmObjectCopy.realmSet$opening(realmObjectSource.realmGet$opening());
        realmObjectCopy.realmSet$closing(realmObjectSource.realmGet$closing());
        realmObjectCopy.realmSet$enabled(realmObjectSource.realmGet$enabled());
        realmObjectCopy.realmSet$timezone(realmObjectSource.realmGet$timezone());
        return realmObject;
    }

    public static long insert(Realm realm, com.directoriodelicias.apps.delicias.classes.OpeningTime object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.OpeningTime.class);
        long tableNativePtr = table.getNativePtr();
        OpeningTimeColumnInfo columnInfo = (OpeningTimeColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.OpeningTime.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((OpeningTimeRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((OpeningTimeRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((OpeningTimeRealmProxyInterface) object).realmGet$id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        Table.nativeSetLong(tableNativePtr, columnInfo.store_idIndex, rowIndex, ((OpeningTimeRealmProxyInterface) object).realmGet$store_id(), false);
        String realmGet$day = ((OpeningTimeRealmProxyInterface) object).realmGet$day();
        if (realmGet$day != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dayIndex, rowIndex, realmGet$day, false);
        }
        String realmGet$opening = ((OpeningTimeRealmProxyInterface) object).realmGet$opening();
        if (realmGet$opening != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.openingIndex, rowIndex, realmGet$opening, false);
        }
        String realmGet$closing = ((OpeningTimeRealmProxyInterface) object).realmGet$closing();
        if (realmGet$closing != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.closingIndex, rowIndex, realmGet$closing, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.enabledIndex, rowIndex, ((OpeningTimeRealmProxyInterface) object).realmGet$enabled(), false);
        String realmGet$timezone = ((OpeningTimeRealmProxyInterface) object).realmGet$timezone();
        if (realmGet$timezone != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.timezoneIndex, rowIndex, realmGet$timezone, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.OpeningTime.class);
        long tableNativePtr = table.getNativePtr();
        OpeningTimeColumnInfo columnInfo = (OpeningTimeColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.OpeningTime.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.OpeningTime object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.OpeningTime) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((OpeningTimeRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((OpeningTimeRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((OpeningTimeRealmProxyInterface) object).realmGet$id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            Table.nativeSetLong(tableNativePtr, columnInfo.store_idIndex, rowIndex, ((OpeningTimeRealmProxyInterface) object).realmGet$store_id(), false);
            String realmGet$day = ((OpeningTimeRealmProxyInterface) object).realmGet$day();
            if (realmGet$day != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dayIndex, rowIndex, realmGet$day, false);
            }
            String realmGet$opening = ((OpeningTimeRealmProxyInterface) object).realmGet$opening();
            if (realmGet$opening != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.openingIndex, rowIndex, realmGet$opening, false);
            }
            String realmGet$closing = ((OpeningTimeRealmProxyInterface) object).realmGet$closing();
            if (realmGet$closing != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.closingIndex, rowIndex, realmGet$closing, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.enabledIndex, rowIndex, ((OpeningTimeRealmProxyInterface) object).realmGet$enabled(), false);
            String realmGet$timezone = ((OpeningTimeRealmProxyInterface) object).realmGet$timezone();
            if (realmGet$timezone != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.timezoneIndex, rowIndex, realmGet$timezone, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.OpeningTime object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.OpeningTime.class);
        long tableNativePtr = table.getNativePtr();
        OpeningTimeColumnInfo columnInfo = (OpeningTimeColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.OpeningTime.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((OpeningTimeRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((OpeningTimeRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((OpeningTimeRealmProxyInterface) object).realmGet$id());
        }
        cache.put(object, rowIndex);
        Table.nativeSetLong(tableNativePtr, columnInfo.store_idIndex, rowIndex, ((OpeningTimeRealmProxyInterface) object).realmGet$store_id(), false);
        String realmGet$day = ((OpeningTimeRealmProxyInterface) object).realmGet$day();
        if (realmGet$day != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dayIndex, rowIndex, realmGet$day, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dayIndex, rowIndex, false);
        }
        String realmGet$opening = ((OpeningTimeRealmProxyInterface) object).realmGet$opening();
        if (realmGet$opening != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.openingIndex, rowIndex, realmGet$opening, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.openingIndex, rowIndex, false);
        }
        String realmGet$closing = ((OpeningTimeRealmProxyInterface) object).realmGet$closing();
        if (realmGet$closing != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.closingIndex, rowIndex, realmGet$closing, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.closingIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.enabledIndex, rowIndex, ((OpeningTimeRealmProxyInterface) object).realmGet$enabled(), false);
        String realmGet$timezone = ((OpeningTimeRealmProxyInterface) object).realmGet$timezone();
        if (realmGet$timezone != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.timezoneIndex, rowIndex, realmGet$timezone, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.timezoneIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.OpeningTime.class);
        long tableNativePtr = table.getNativePtr();
        OpeningTimeColumnInfo columnInfo = (OpeningTimeColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.OpeningTime.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.OpeningTime object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.OpeningTime) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((OpeningTimeRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((OpeningTimeRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((OpeningTimeRealmProxyInterface) object).realmGet$id());
            }
            cache.put(object, rowIndex);
            Table.nativeSetLong(tableNativePtr, columnInfo.store_idIndex, rowIndex, ((OpeningTimeRealmProxyInterface) object).realmGet$store_id(), false);
            String realmGet$day = ((OpeningTimeRealmProxyInterface) object).realmGet$day();
            if (realmGet$day != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dayIndex, rowIndex, realmGet$day, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dayIndex, rowIndex, false);
            }
            String realmGet$opening = ((OpeningTimeRealmProxyInterface) object).realmGet$opening();
            if (realmGet$opening != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.openingIndex, rowIndex, realmGet$opening, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.openingIndex, rowIndex, false);
            }
            String realmGet$closing = ((OpeningTimeRealmProxyInterface) object).realmGet$closing();
            if (realmGet$closing != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.closingIndex, rowIndex, realmGet$closing, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.closingIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.enabledIndex, rowIndex, ((OpeningTimeRealmProxyInterface) object).realmGet$enabled(), false);
            String realmGet$timezone = ((OpeningTimeRealmProxyInterface) object).realmGet$timezone();
            if (realmGet$timezone != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.timezoneIndex, rowIndex, realmGet$timezone, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.timezoneIndex, rowIndex, false);
            }
        }
    }

    public static com.directoriodelicias.apps.delicias.classes.OpeningTime createDetachedCopy(com.directoriodelicias.apps.delicias.classes.OpeningTime realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.directoriodelicias.apps.delicias.classes.OpeningTime unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.directoriodelicias.apps.delicias.classes.OpeningTime();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.directoriodelicias.apps.delicias.classes.OpeningTime) cachedObject.object;
            }
            unmanagedObject = (com.directoriodelicias.apps.delicias.classes.OpeningTime) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        OpeningTimeRealmProxyInterface unmanagedCopy = (OpeningTimeRealmProxyInterface) unmanagedObject;
        OpeningTimeRealmProxyInterface realmSource = (OpeningTimeRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());
        unmanagedCopy.realmSet$store_id(realmSource.realmGet$store_id());
        unmanagedCopy.realmSet$day(realmSource.realmGet$day());
        unmanagedCopy.realmSet$opening(realmSource.realmGet$opening());
        unmanagedCopy.realmSet$closing(realmSource.realmGet$closing());
        unmanagedCopy.realmSet$enabled(realmSource.realmGet$enabled());
        unmanagedCopy.realmSet$timezone(realmSource.realmGet$timezone());

        return unmanagedObject;
    }

    static com.directoriodelicias.apps.delicias.classes.OpeningTime update(Realm realm, com.directoriodelicias.apps.delicias.classes.OpeningTime realmObject, com.directoriodelicias.apps.delicias.classes.OpeningTime newObject, Map<RealmModel, RealmObjectProxy> cache) {
        OpeningTimeRealmProxyInterface realmObjectTarget = (OpeningTimeRealmProxyInterface) realmObject;
        OpeningTimeRealmProxyInterface realmObjectSource = (OpeningTimeRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$store_id(realmObjectSource.realmGet$store_id());
        realmObjectTarget.realmSet$day(realmObjectSource.realmGet$day());
        realmObjectTarget.realmSet$opening(realmObjectSource.realmGet$opening());
        realmObjectTarget.realmSet$closing(realmObjectSource.realmGet$closing());
        realmObjectTarget.realmSet$enabled(realmObjectSource.realmGet$enabled());
        realmObjectTarget.realmSet$timezone(realmObjectSource.realmGet$timezone());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("OpeningTime = proxy[");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{store_id:");
        stringBuilder.append(realmGet$store_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{day:");
        stringBuilder.append(realmGet$day() != null ? realmGet$day() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{opening:");
        stringBuilder.append(realmGet$opening() != null ? realmGet$opening() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{closing:");
        stringBuilder.append(realmGet$closing() != null ? realmGet$closing() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{enabled:");
        stringBuilder.append(realmGet$enabled());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{timezone:");
        stringBuilder.append(realmGet$timezone() != null ? realmGet$timezone() : "null");
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
        OpeningTimeRealmProxy aOpeningTime = (OpeningTimeRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aOpeningTime.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aOpeningTime.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aOpeningTime.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
