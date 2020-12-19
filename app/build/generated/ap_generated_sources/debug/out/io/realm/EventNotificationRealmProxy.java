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
public class EventNotificationRealmProxy extends com.directoriodelicias.apps.delicias.classes.EventNotification
    implements RealmObjectProxy, EventNotificationRealmProxyInterface {

    static final class EventNotificationColumnInfo extends ColumnInfo {
        long idIndex;
        long eventIndex;

        EventNotificationColumnInfo(OsSchemaInfo schemaInfo) {
            super(2);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("EventNotification");
            this.idIndex = addColumnDetails("id", objectSchemaInfo);
            this.eventIndex = addColumnDetails("event", objectSchemaInfo);
        }

        EventNotificationColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new EventNotificationColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final EventNotificationColumnInfo src = (EventNotificationColumnInfo) rawSrc;
            final EventNotificationColumnInfo dst = (EventNotificationColumnInfo) rawDst;
            dst.idIndex = src.idIndex;
            dst.eventIndex = src.eventIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(2);
        fieldNames.add("id");
        fieldNames.add("event");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private EventNotificationColumnInfo columnInfo;
    private ProxyState<com.directoriodelicias.apps.delicias.classes.EventNotification> proxyState;

    EventNotificationRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (EventNotificationColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.directoriodelicias.apps.delicias.classes.EventNotification>(this);
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
    public com.directoriodelicias.apps.delicias.classes.Event realmGet$event() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.eventIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(com.directoriodelicias.apps.delicias.classes.Event.class, proxyState.getRow$realm().getLink(columnInfo.eventIndex), false, Collections.<String>emptyList());
    }

    @Override
    public void realmSet$event(com.directoriodelicias.apps.delicias.classes.Event value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("event")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.eventIndex);
                return;
            }
            proxyState.checkValidObject(value);
            row.getTable().setLink(columnInfo.eventIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.eventIndex);
            return;
        }
        proxyState.checkValidObject(value);
        proxyState.getRow$realm().setLink(columnInfo.eventIndex, ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex());
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("EventNotification", 2, 0);
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedLinkProperty("event", RealmFieldType.OBJECT, "Event");
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static EventNotificationColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new EventNotificationColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "EventNotification";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.directoriodelicias.apps.delicias.classes.EventNotification createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(1);
        com.directoriodelicias.apps.delicias.classes.EventNotification obj = null;
        if (update) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.EventNotification.class);
            EventNotificationColumnInfo columnInfo = (EventNotificationColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.EventNotification.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.EventNotification.class), false, Collections.<String> emptyList());
                    obj = new io.realm.EventNotificationRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("event")) {
                excludeFields.add("event");
            }
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.EventNotificationRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.EventNotification.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.EventNotificationRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.EventNotification.class, json.getInt("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }

        final EventNotificationRealmProxyInterface objProxy = (EventNotificationRealmProxyInterface) obj;
        if (json.has("event")) {
            if (json.isNull("event")) {
                objProxy.realmSet$event(null);
            } else {
                com.directoriodelicias.apps.delicias.classes.Event eventObj = EventRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("event"), update);
                objProxy.realmSet$event(eventObj);
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.directoriodelicias.apps.delicias.classes.EventNotification createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.directoriodelicias.apps.delicias.classes.EventNotification obj = new com.directoriodelicias.apps.delicias.classes.EventNotification();
        final EventNotificationRealmProxyInterface objProxy = (EventNotificationRealmProxyInterface) obj;
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
            } else if (name.equals("event")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$event(null);
                } else {
                    com.directoriodelicias.apps.delicias.classes.Event eventObj = EventRealmProxy.createUsingJsonStream(realm, reader);
                    objProxy.realmSet$event(eventObj);
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

    public static com.directoriodelicias.apps.delicias.classes.EventNotification copyOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.EventNotification object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.directoriodelicias.apps.delicias.classes.EventNotification) cachedRealmObject;
        }

        com.directoriodelicias.apps.delicias.classes.EventNotification realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.EventNotification.class);
            EventNotificationColumnInfo columnInfo = (EventNotificationColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.EventNotification.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((EventNotificationRealmProxyInterface) object).realmGet$id());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.EventNotification.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.EventNotificationRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.directoriodelicias.apps.delicias.classes.EventNotification copy(Realm realm, com.directoriodelicias.apps.delicias.classes.EventNotification newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.directoriodelicias.apps.delicias.classes.EventNotification) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.directoriodelicias.apps.delicias.classes.EventNotification realmObject = realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.EventNotification.class, ((EventNotificationRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        EventNotificationRealmProxyInterface realmObjectSource = (EventNotificationRealmProxyInterface) newObject;
        EventNotificationRealmProxyInterface realmObjectCopy = (EventNotificationRealmProxyInterface) realmObject;


        com.directoriodelicias.apps.delicias.classes.Event eventObj = realmObjectSource.realmGet$event();
        if (eventObj == null) {
            realmObjectCopy.realmSet$event(null);
        } else {
            com.directoriodelicias.apps.delicias.classes.Event cacheevent = (com.directoriodelicias.apps.delicias.classes.Event) cache.get(eventObj);
            if (cacheevent != null) {
                realmObjectCopy.realmSet$event(cacheevent);
            } else {
                realmObjectCopy.realmSet$event(EventRealmProxy.copyOrUpdate(realm, eventObj, update, cache));
            }
        }
        return realmObject;
    }

    public static long insert(Realm realm, com.directoriodelicias.apps.delicias.classes.EventNotification object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.EventNotification.class);
        long tableNativePtr = table.getNativePtr();
        EventNotificationColumnInfo columnInfo = (EventNotificationColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.EventNotification.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((EventNotificationRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((EventNotificationRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((EventNotificationRealmProxyInterface) object).realmGet$id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);

        com.directoriodelicias.apps.delicias.classes.Event eventObj = ((EventNotificationRealmProxyInterface) object).realmGet$event();
        if (eventObj != null) {
            Long cacheevent = cache.get(eventObj);
            if (cacheevent == null) {
                cacheevent = EventRealmProxy.insert(realm, eventObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.eventIndex, rowIndex, cacheevent, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.EventNotification.class);
        long tableNativePtr = table.getNativePtr();
        EventNotificationColumnInfo columnInfo = (EventNotificationColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.EventNotification.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.EventNotification object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.EventNotification) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((EventNotificationRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((EventNotificationRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((EventNotificationRealmProxyInterface) object).realmGet$id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);

            com.directoriodelicias.apps.delicias.classes.Event eventObj = ((EventNotificationRealmProxyInterface) object).realmGet$event();
            if (eventObj != null) {
                Long cacheevent = cache.get(eventObj);
                if (cacheevent == null) {
                    cacheevent = EventRealmProxy.insert(realm, eventObj, cache);
                }
                table.setLink(columnInfo.eventIndex, rowIndex, cacheevent, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.EventNotification object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.EventNotification.class);
        long tableNativePtr = table.getNativePtr();
        EventNotificationColumnInfo columnInfo = (EventNotificationColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.EventNotification.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((EventNotificationRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((EventNotificationRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((EventNotificationRealmProxyInterface) object).realmGet$id());
        }
        cache.put(object, rowIndex);

        com.directoriodelicias.apps.delicias.classes.Event eventObj = ((EventNotificationRealmProxyInterface) object).realmGet$event();
        if (eventObj != null) {
            Long cacheevent = cache.get(eventObj);
            if (cacheevent == null) {
                cacheevent = EventRealmProxy.insertOrUpdate(realm, eventObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.eventIndex, rowIndex, cacheevent, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.eventIndex, rowIndex);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.EventNotification.class);
        long tableNativePtr = table.getNativePtr();
        EventNotificationColumnInfo columnInfo = (EventNotificationColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.EventNotification.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.EventNotification object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.EventNotification) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((EventNotificationRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((EventNotificationRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((EventNotificationRealmProxyInterface) object).realmGet$id());
            }
            cache.put(object, rowIndex);

            com.directoriodelicias.apps.delicias.classes.Event eventObj = ((EventNotificationRealmProxyInterface) object).realmGet$event();
            if (eventObj != null) {
                Long cacheevent = cache.get(eventObj);
                if (cacheevent == null) {
                    cacheevent = EventRealmProxy.insertOrUpdate(realm, eventObj, cache);
                }
                Table.nativeSetLink(tableNativePtr, columnInfo.eventIndex, rowIndex, cacheevent, false);
            } else {
                Table.nativeNullifyLink(tableNativePtr, columnInfo.eventIndex, rowIndex);
            }
        }
    }

    public static com.directoriodelicias.apps.delicias.classes.EventNotification createDetachedCopy(com.directoriodelicias.apps.delicias.classes.EventNotification realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.directoriodelicias.apps.delicias.classes.EventNotification unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.directoriodelicias.apps.delicias.classes.EventNotification();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.directoriodelicias.apps.delicias.classes.EventNotification) cachedObject.object;
            }
            unmanagedObject = (com.directoriodelicias.apps.delicias.classes.EventNotification) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        EventNotificationRealmProxyInterface unmanagedCopy = (EventNotificationRealmProxyInterface) unmanagedObject;
        EventNotificationRealmProxyInterface realmSource = (EventNotificationRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());

        // Deep copy of event
        unmanagedCopy.realmSet$event(EventRealmProxy.createDetachedCopy(realmSource.realmGet$event(), currentDepth + 1, maxDepth, cache));

        return unmanagedObject;
    }

    static com.directoriodelicias.apps.delicias.classes.EventNotification update(Realm realm, com.directoriodelicias.apps.delicias.classes.EventNotification realmObject, com.directoriodelicias.apps.delicias.classes.EventNotification newObject, Map<RealmModel, RealmObjectProxy> cache) {
        EventNotificationRealmProxyInterface realmObjectTarget = (EventNotificationRealmProxyInterface) realmObject;
        EventNotificationRealmProxyInterface realmObjectSource = (EventNotificationRealmProxyInterface) newObject;
        com.directoriodelicias.apps.delicias.classes.Event eventObj = realmObjectSource.realmGet$event();
        if (eventObj == null) {
            realmObjectTarget.realmSet$event(null);
        } else {
            com.directoriodelicias.apps.delicias.classes.Event cacheevent = (com.directoriodelicias.apps.delicias.classes.Event) cache.get(eventObj);
            if (cacheevent != null) {
                realmObjectTarget.realmSet$event(cacheevent);
            } else {
                realmObjectTarget.realmSet$event(EventRealmProxy.copyOrUpdate(realm, eventObj, true, cache));
            }
        }
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("EventNotification = proxy[");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{event:");
        stringBuilder.append(realmGet$event() != null ? "Event" : "null");
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
        EventNotificationRealmProxy aEventNotification = (EventNotificationRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aEventNotification.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aEventNotification.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aEventNotification.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
