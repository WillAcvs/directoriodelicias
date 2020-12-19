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
public class UpComingEventRealmProxy extends com.directoriodelicias.apps.delicias.classes.UpComingEvent
    implements RealmObjectProxy, UpComingEventRealmProxyInterface {

    static final class UpComingEventColumnInfo extends ColumnInfo {
        long event_idIndex;
        long event_nameIndex;
        long begin_atIndex;
        long notifiedIndex;

        UpComingEventColumnInfo(OsSchemaInfo schemaInfo) {
            super(4);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("UpComingEvent");
            this.event_idIndex = addColumnDetails("event_id", objectSchemaInfo);
            this.event_nameIndex = addColumnDetails("event_name", objectSchemaInfo);
            this.begin_atIndex = addColumnDetails("begin_at", objectSchemaInfo);
            this.notifiedIndex = addColumnDetails("notified", objectSchemaInfo);
        }

        UpComingEventColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new UpComingEventColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final UpComingEventColumnInfo src = (UpComingEventColumnInfo) rawSrc;
            final UpComingEventColumnInfo dst = (UpComingEventColumnInfo) rawDst;
            dst.event_idIndex = src.event_idIndex;
            dst.event_nameIndex = src.event_nameIndex;
            dst.begin_atIndex = src.begin_atIndex;
            dst.notifiedIndex = src.notifiedIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(4);
        fieldNames.add("event_id");
        fieldNames.add("event_name");
        fieldNames.add("begin_at");
        fieldNames.add("notified");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private UpComingEventColumnInfo columnInfo;
    private ProxyState<com.directoriodelicias.apps.delicias.classes.UpComingEvent> proxyState;

    UpComingEventRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (UpComingEventColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.directoriodelicias.apps.delicias.classes.UpComingEvent>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$event_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.event_idIndex);
    }

    @Override
    public void realmSet$event_id(int value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'event_id' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$event_name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.event_nameIndex);
    }

    @Override
    public void realmSet$event_name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.event_nameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.event_nameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.event_nameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.event_nameIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$begin_at() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.begin_atIndex);
    }

    @Override
    public void realmSet$begin_at(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.begin_atIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.begin_atIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.begin_atIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.begin_atIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Boolean realmGet$notified() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.notifiedIndex)) {
            return null;
        }
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.notifiedIndex);
    }

    @Override
    public void realmSet$notified(Boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.notifiedIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBoolean(columnInfo.notifiedIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.notifiedIndex);
            return;
        }
        proxyState.getRow$realm().setBoolean(columnInfo.notifiedIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("UpComingEvent", 4, 0);
        builder.addPersistedProperty("event_id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("event_name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("begin_at", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("notified", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static UpComingEventColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new UpComingEventColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "UpComingEvent";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.directoriodelicias.apps.delicias.classes.UpComingEvent createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.directoriodelicias.apps.delicias.classes.UpComingEvent obj = null;
        if (update) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.UpComingEvent.class);
            UpComingEventColumnInfo columnInfo = (UpComingEventColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.UpComingEvent.class);
            long pkColumnIndex = columnInfo.event_idIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("event_id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("event_id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.UpComingEvent.class), false, Collections.<String> emptyList());
                    obj = new io.realm.UpComingEventRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("event_id")) {
                if (json.isNull("event_id")) {
                    obj = (io.realm.UpComingEventRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.UpComingEvent.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.UpComingEventRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.UpComingEvent.class, json.getInt("event_id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'event_id'.");
            }
        }

        final UpComingEventRealmProxyInterface objProxy = (UpComingEventRealmProxyInterface) obj;
        if (json.has("event_name")) {
            if (json.isNull("event_name")) {
                objProxy.realmSet$event_name(null);
            } else {
                objProxy.realmSet$event_name((String) json.getString("event_name"));
            }
        }
        if (json.has("begin_at")) {
            if (json.isNull("begin_at")) {
                objProxy.realmSet$begin_at(null);
            } else {
                objProxy.realmSet$begin_at((String) json.getString("begin_at"));
            }
        }
        if (json.has("notified")) {
            if (json.isNull("notified")) {
                objProxy.realmSet$notified(null);
            } else {
                objProxy.realmSet$notified((boolean) json.getBoolean("notified"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.directoriodelicias.apps.delicias.classes.UpComingEvent createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.directoriodelicias.apps.delicias.classes.UpComingEvent obj = new com.directoriodelicias.apps.delicias.classes.UpComingEvent();
        final UpComingEventRealmProxyInterface objProxy = (UpComingEventRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("event_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$event_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'event_id' to null.");
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("event_name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$event_name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$event_name(null);
                }
            } else if (name.equals("begin_at")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$begin_at((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$begin_at(null);
                }
            } else if (name.equals("notified")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$notified((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$notified(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'event_id'.");
        }
        return realm.copyToRealm(obj);
    }

    public static com.directoriodelicias.apps.delicias.classes.UpComingEvent copyOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.UpComingEvent object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.directoriodelicias.apps.delicias.classes.UpComingEvent) cachedRealmObject;
        }

        com.directoriodelicias.apps.delicias.classes.UpComingEvent realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.UpComingEvent.class);
            UpComingEventColumnInfo columnInfo = (UpComingEventColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.UpComingEvent.class);
            long pkColumnIndex = columnInfo.event_idIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((UpComingEventRealmProxyInterface) object).realmGet$event_id());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.UpComingEvent.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.UpComingEventRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.directoriodelicias.apps.delicias.classes.UpComingEvent copy(Realm realm, com.directoriodelicias.apps.delicias.classes.UpComingEvent newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.directoriodelicias.apps.delicias.classes.UpComingEvent) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.directoriodelicias.apps.delicias.classes.UpComingEvent realmObject = realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.UpComingEvent.class, ((UpComingEventRealmProxyInterface) newObject).realmGet$event_id(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        UpComingEventRealmProxyInterface realmObjectSource = (UpComingEventRealmProxyInterface) newObject;
        UpComingEventRealmProxyInterface realmObjectCopy = (UpComingEventRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$event_name(realmObjectSource.realmGet$event_name());
        realmObjectCopy.realmSet$begin_at(realmObjectSource.realmGet$begin_at());
        realmObjectCopy.realmSet$notified(realmObjectSource.realmGet$notified());
        return realmObject;
    }

    public static long insert(Realm realm, com.directoriodelicias.apps.delicias.classes.UpComingEvent object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.UpComingEvent.class);
        long tableNativePtr = table.getNativePtr();
        UpComingEventColumnInfo columnInfo = (UpComingEventColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.UpComingEvent.class);
        long pkColumnIndex = columnInfo.event_idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((UpComingEventRealmProxyInterface) object).realmGet$event_id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((UpComingEventRealmProxyInterface) object).realmGet$event_id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((UpComingEventRealmProxyInterface) object).realmGet$event_id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$event_name = ((UpComingEventRealmProxyInterface) object).realmGet$event_name();
        if (realmGet$event_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.event_nameIndex, rowIndex, realmGet$event_name, false);
        }
        String realmGet$begin_at = ((UpComingEventRealmProxyInterface) object).realmGet$begin_at();
        if (realmGet$begin_at != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.begin_atIndex, rowIndex, realmGet$begin_at, false);
        }
        Boolean realmGet$notified = ((UpComingEventRealmProxyInterface) object).realmGet$notified();
        if (realmGet$notified != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.notifiedIndex, rowIndex, realmGet$notified, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.UpComingEvent.class);
        long tableNativePtr = table.getNativePtr();
        UpComingEventColumnInfo columnInfo = (UpComingEventColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.UpComingEvent.class);
        long pkColumnIndex = columnInfo.event_idIndex;
        com.directoriodelicias.apps.delicias.classes.UpComingEvent object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.UpComingEvent) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((UpComingEventRealmProxyInterface) object).realmGet$event_id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((UpComingEventRealmProxyInterface) object).realmGet$event_id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((UpComingEventRealmProxyInterface) object).realmGet$event_id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$event_name = ((UpComingEventRealmProxyInterface) object).realmGet$event_name();
            if (realmGet$event_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.event_nameIndex, rowIndex, realmGet$event_name, false);
            }
            String realmGet$begin_at = ((UpComingEventRealmProxyInterface) object).realmGet$begin_at();
            if (realmGet$begin_at != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.begin_atIndex, rowIndex, realmGet$begin_at, false);
            }
            Boolean realmGet$notified = ((UpComingEventRealmProxyInterface) object).realmGet$notified();
            if (realmGet$notified != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.notifiedIndex, rowIndex, realmGet$notified, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.UpComingEvent object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.UpComingEvent.class);
        long tableNativePtr = table.getNativePtr();
        UpComingEventColumnInfo columnInfo = (UpComingEventColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.UpComingEvent.class);
        long pkColumnIndex = columnInfo.event_idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((UpComingEventRealmProxyInterface) object).realmGet$event_id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((UpComingEventRealmProxyInterface) object).realmGet$event_id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((UpComingEventRealmProxyInterface) object).realmGet$event_id());
        }
        cache.put(object, rowIndex);
        String realmGet$event_name = ((UpComingEventRealmProxyInterface) object).realmGet$event_name();
        if (realmGet$event_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.event_nameIndex, rowIndex, realmGet$event_name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.event_nameIndex, rowIndex, false);
        }
        String realmGet$begin_at = ((UpComingEventRealmProxyInterface) object).realmGet$begin_at();
        if (realmGet$begin_at != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.begin_atIndex, rowIndex, realmGet$begin_at, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.begin_atIndex, rowIndex, false);
        }
        Boolean realmGet$notified = ((UpComingEventRealmProxyInterface) object).realmGet$notified();
        if (realmGet$notified != null) {
            Table.nativeSetBoolean(tableNativePtr, columnInfo.notifiedIndex, rowIndex, realmGet$notified, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.notifiedIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.UpComingEvent.class);
        long tableNativePtr = table.getNativePtr();
        UpComingEventColumnInfo columnInfo = (UpComingEventColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.UpComingEvent.class);
        long pkColumnIndex = columnInfo.event_idIndex;
        com.directoriodelicias.apps.delicias.classes.UpComingEvent object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.UpComingEvent) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((UpComingEventRealmProxyInterface) object).realmGet$event_id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((UpComingEventRealmProxyInterface) object).realmGet$event_id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((UpComingEventRealmProxyInterface) object).realmGet$event_id());
            }
            cache.put(object, rowIndex);
            String realmGet$event_name = ((UpComingEventRealmProxyInterface) object).realmGet$event_name();
            if (realmGet$event_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.event_nameIndex, rowIndex, realmGet$event_name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.event_nameIndex, rowIndex, false);
            }
            String realmGet$begin_at = ((UpComingEventRealmProxyInterface) object).realmGet$begin_at();
            if (realmGet$begin_at != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.begin_atIndex, rowIndex, realmGet$begin_at, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.begin_atIndex, rowIndex, false);
            }
            Boolean realmGet$notified = ((UpComingEventRealmProxyInterface) object).realmGet$notified();
            if (realmGet$notified != null) {
                Table.nativeSetBoolean(tableNativePtr, columnInfo.notifiedIndex, rowIndex, realmGet$notified, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.notifiedIndex, rowIndex, false);
            }
        }
    }

    public static com.directoriodelicias.apps.delicias.classes.UpComingEvent createDetachedCopy(com.directoriodelicias.apps.delicias.classes.UpComingEvent realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.directoriodelicias.apps.delicias.classes.UpComingEvent unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.directoriodelicias.apps.delicias.classes.UpComingEvent();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.directoriodelicias.apps.delicias.classes.UpComingEvent) cachedObject.object;
            }
            unmanagedObject = (com.directoriodelicias.apps.delicias.classes.UpComingEvent) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        UpComingEventRealmProxyInterface unmanagedCopy = (UpComingEventRealmProxyInterface) unmanagedObject;
        UpComingEventRealmProxyInterface realmSource = (UpComingEventRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$event_id(realmSource.realmGet$event_id());
        unmanagedCopy.realmSet$event_name(realmSource.realmGet$event_name());
        unmanagedCopy.realmSet$begin_at(realmSource.realmGet$begin_at());
        unmanagedCopy.realmSet$notified(realmSource.realmGet$notified());

        return unmanagedObject;
    }

    static com.directoriodelicias.apps.delicias.classes.UpComingEvent update(Realm realm, com.directoriodelicias.apps.delicias.classes.UpComingEvent realmObject, com.directoriodelicias.apps.delicias.classes.UpComingEvent newObject, Map<RealmModel, RealmObjectProxy> cache) {
        UpComingEventRealmProxyInterface realmObjectTarget = (UpComingEventRealmProxyInterface) realmObject;
        UpComingEventRealmProxyInterface realmObjectSource = (UpComingEventRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$event_name(realmObjectSource.realmGet$event_name());
        realmObjectTarget.realmSet$begin_at(realmObjectSource.realmGet$begin_at());
        realmObjectTarget.realmSet$notified(realmObjectSource.realmGet$notified());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("UpComingEvent = proxy[");
        stringBuilder.append("{event_id:");
        stringBuilder.append(realmGet$event_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{event_name:");
        stringBuilder.append(realmGet$event_name() != null ? realmGet$event_name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{begin_at:");
        stringBuilder.append(realmGet$begin_at() != null ? realmGet$begin_at() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{notified:");
        stringBuilder.append(realmGet$notified() != null ? realmGet$notified() : "null");
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
        UpComingEventRealmProxy aUpComingEvent = (UpComingEventRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aUpComingEvent.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aUpComingEvent.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aUpComingEvent.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
