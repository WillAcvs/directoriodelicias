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
public class SessionRealmProxy extends com.directoriodelicias.apps.delicias.classes.Session
    implements RealmObjectProxy, SessionRealmProxyInterface {

    static final class SessionColumnInfo extends ColumnInfo {
        long sessionIdIndex;
        long userIndex;

        SessionColumnInfo(OsSchemaInfo schemaInfo) {
            super(2);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("Session");
            this.sessionIdIndex = addColumnDetails("sessionId", objectSchemaInfo);
            this.userIndex = addColumnDetails("user", objectSchemaInfo);
        }

        SessionColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new SessionColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final SessionColumnInfo src = (SessionColumnInfo) rawSrc;
            final SessionColumnInfo dst = (SessionColumnInfo) rawDst;
            dst.sessionIdIndex = src.sessionIdIndex;
            dst.userIndex = src.userIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(2);
        fieldNames.add("sessionId");
        fieldNames.add("user");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private SessionColumnInfo columnInfo;
    private ProxyState<com.directoriodelicias.apps.delicias.classes.Session> proxyState;

    SessionRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (SessionColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.directoriodelicias.apps.delicias.classes.Session>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$sessionId() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.sessionIdIndex);
    }

    @Override
    public void realmSet$sessionId(int value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'sessionId' cannot be changed after object was created.");
    }

    @Override
    public com.directoriodelicias.apps.delicias.classes.User realmGet$user() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.userIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(com.directoriodelicias.apps.delicias.classes.User.class, proxyState.getRow$realm().getLink(columnInfo.userIndex), false, Collections.<String>emptyList());
    }

    @Override
    public void realmSet$user(com.directoriodelicias.apps.delicias.classes.User value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("user")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.userIndex);
                return;
            }
            proxyState.checkValidObject(value);
            row.getTable().setLink(columnInfo.userIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.userIndex);
            return;
        }
        proxyState.checkValidObject(value);
        proxyState.getRow$realm().setLink(columnInfo.userIndex, ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex());
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("Session", 2, 0);
        builder.addPersistedProperty("sessionId", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedLinkProperty("user", RealmFieldType.OBJECT, "User");
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static SessionColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new SessionColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "Session";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.directoriodelicias.apps.delicias.classes.Session createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(1);
        com.directoriodelicias.apps.delicias.classes.Session obj = null;
        if (update) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Session.class);
            SessionColumnInfo columnInfo = (SessionColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Session.class);
            long pkColumnIndex = columnInfo.sessionIdIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("sessionId")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("sessionId"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Session.class), false, Collections.<String> emptyList());
                    obj = new io.realm.SessionRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("user")) {
                excludeFields.add("user");
            }
            if (json.has("sessionId")) {
                if (json.isNull("sessionId")) {
                    obj = (io.realm.SessionRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Session.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.SessionRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Session.class, json.getInt("sessionId"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'sessionId'.");
            }
        }

        final SessionRealmProxyInterface objProxy = (SessionRealmProxyInterface) obj;
        if (json.has("user")) {
            if (json.isNull("user")) {
                objProxy.realmSet$user(null);
            } else {
                com.directoriodelicias.apps.delicias.classes.User userObj = UserRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("user"), update);
                objProxy.realmSet$user(userObj);
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.directoriodelicias.apps.delicias.classes.Session createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.directoriodelicias.apps.delicias.classes.Session obj = new com.directoriodelicias.apps.delicias.classes.Session();
        final SessionRealmProxyInterface objProxy = (SessionRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("sessionId")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$sessionId((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'sessionId' to null.");
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("user")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$user(null);
                } else {
                    com.directoriodelicias.apps.delicias.classes.User userObj = UserRealmProxy.createUsingJsonStream(realm, reader);
                    objProxy.realmSet$user(userObj);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'sessionId'.");
        }
        return realm.copyToRealm(obj);
    }

    public static com.directoriodelicias.apps.delicias.classes.Session copyOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Session object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.directoriodelicias.apps.delicias.classes.Session) cachedRealmObject;
        }

        com.directoriodelicias.apps.delicias.classes.Session realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Session.class);
            SessionColumnInfo columnInfo = (SessionColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Session.class);
            long pkColumnIndex = columnInfo.sessionIdIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((SessionRealmProxyInterface) object).realmGet$sessionId());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Session.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.SessionRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.directoriodelicias.apps.delicias.classes.Session copy(Realm realm, com.directoriodelicias.apps.delicias.classes.Session newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.directoriodelicias.apps.delicias.classes.Session) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.directoriodelicias.apps.delicias.classes.Session realmObject = realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Session.class, ((SessionRealmProxyInterface) newObject).realmGet$sessionId(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        SessionRealmProxyInterface realmObjectSource = (SessionRealmProxyInterface) newObject;
        SessionRealmProxyInterface realmObjectCopy = (SessionRealmProxyInterface) realmObject;


        com.directoriodelicias.apps.delicias.classes.User userObj = realmObjectSource.realmGet$user();
        if (userObj == null) {
            realmObjectCopy.realmSet$user(null);
        } else {
            com.directoriodelicias.apps.delicias.classes.User cacheuser = (com.directoriodelicias.apps.delicias.classes.User) cache.get(userObj);
            if (cacheuser != null) {
                realmObjectCopy.realmSet$user(cacheuser);
            } else {
                realmObjectCopy.realmSet$user(UserRealmProxy.copyOrUpdate(realm, userObj, update, cache));
            }
        }
        return realmObject;
    }

    public static long insert(Realm realm, com.directoriodelicias.apps.delicias.classes.Session object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Session.class);
        long tableNativePtr = table.getNativePtr();
        SessionColumnInfo columnInfo = (SessionColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Session.class);
        long pkColumnIndex = columnInfo.sessionIdIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((SessionRealmProxyInterface) object).realmGet$sessionId();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((SessionRealmProxyInterface) object).realmGet$sessionId());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((SessionRealmProxyInterface) object).realmGet$sessionId());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);

        com.directoriodelicias.apps.delicias.classes.User userObj = ((SessionRealmProxyInterface) object).realmGet$user();
        if (userObj != null) {
            Long cacheuser = cache.get(userObj);
            if (cacheuser == null) {
                cacheuser = UserRealmProxy.insert(realm, userObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.userIndex, rowIndex, cacheuser, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Session.class);
        long tableNativePtr = table.getNativePtr();
        SessionColumnInfo columnInfo = (SessionColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Session.class);
        long pkColumnIndex = columnInfo.sessionIdIndex;
        com.directoriodelicias.apps.delicias.classes.Session object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Session) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((SessionRealmProxyInterface) object).realmGet$sessionId();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((SessionRealmProxyInterface) object).realmGet$sessionId());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((SessionRealmProxyInterface) object).realmGet$sessionId());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);

            com.directoriodelicias.apps.delicias.classes.User userObj = ((SessionRealmProxyInterface) object).realmGet$user();
            if (userObj != null) {
                Long cacheuser = cache.get(userObj);
                if (cacheuser == null) {
                    cacheuser = UserRealmProxy.insert(realm, userObj, cache);
                }
                table.setLink(columnInfo.userIndex, rowIndex, cacheuser, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Session object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Session.class);
        long tableNativePtr = table.getNativePtr();
        SessionColumnInfo columnInfo = (SessionColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Session.class);
        long pkColumnIndex = columnInfo.sessionIdIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((SessionRealmProxyInterface) object).realmGet$sessionId();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((SessionRealmProxyInterface) object).realmGet$sessionId());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((SessionRealmProxyInterface) object).realmGet$sessionId());
        }
        cache.put(object, rowIndex);

        com.directoriodelicias.apps.delicias.classes.User userObj = ((SessionRealmProxyInterface) object).realmGet$user();
        if (userObj != null) {
            Long cacheuser = cache.get(userObj);
            if (cacheuser == null) {
                cacheuser = UserRealmProxy.insertOrUpdate(realm, userObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.userIndex, rowIndex, cacheuser, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.userIndex, rowIndex);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Session.class);
        long tableNativePtr = table.getNativePtr();
        SessionColumnInfo columnInfo = (SessionColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Session.class);
        long pkColumnIndex = columnInfo.sessionIdIndex;
        com.directoriodelicias.apps.delicias.classes.Session object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Session) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((SessionRealmProxyInterface) object).realmGet$sessionId();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((SessionRealmProxyInterface) object).realmGet$sessionId());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((SessionRealmProxyInterface) object).realmGet$sessionId());
            }
            cache.put(object, rowIndex);

            com.directoriodelicias.apps.delicias.classes.User userObj = ((SessionRealmProxyInterface) object).realmGet$user();
            if (userObj != null) {
                Long cacheuser = cache.get(userObj);
                if (cacheuser == null) {
                    cacheuser = UserRealmProxy.insertOrUpdate(realm, userObj, cache);
                }
                Table.nativeSetLink(tableNativePtr, columnInfo.userIndex, rowIndex, cacheuser, false);
            } else {
                Table.nativeNullifyLink(tableNativePtr, columnInfo.userIndex, rowIndex);
            }
        }
    }

    public static com.directoriodelicias.apps.delicias.classes.Session createDetachedCopy(com.directoriodelicias.apps.delicias.classes.Session realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.directoriodelicias.apps.delicias.classes.Session unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.directoriodelicias.apps.delicias.classes.Session();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.directoriodelicias.apps.delicias.classes.Session) cachedObject.object;
            }
            unmanagedObject = (com.directoriodelicias.apps.delicias.classes.Session) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        SessionRealmProxyInterface unmanagedCopy = (SessionRealmProxyInterface) unmanagedObject;
        SessionRealmProxyInterface realmSource = (SessionRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$sessionId(realmSource.realmGet$sessionId());

        // Deep copy of user
        unmanagedCopy.realmSet$user(UserRealmProxy.createDetachedCopy(realmSource.realmGet$user(), currentDepth + 1, maxDepth, cache));

        return unmanagedObject;
    }

    static com.directoriodelicias.apps.delicias.classes.Session update(Realm realm, com.directoriodelicias.apps.delicias.classes.Session realmObject, com.directoriodelicias.apps.delicias.classes.Session newObject, Map<RealmModel, RealmObjectProxy> cache) {
        SessionRealmProxyInterface realmObjectTarget = (SessionRealmProxyInterface) realmObject;
        SessionRealmProxyInterface realmObjectSource = (SessionRealmProxyInterface) newObject;
        com.directoriodelicias.apps.delicias.classes.User userObj = realmObjectSource.realmGet$user();
        if (userObj == null) {
            realmObjectTarget.realmSet$user(null);
        } else {
            com.directoriodelicias.apps.delicias.classes.User cacheuser = (com.directoriodelicias.apps.delicias.classes.User) cache.get(userObj);
            if (cacheuser != null) {
                realmObjectTarget.realmSet$user(cacheuser);
            } else {
                realmObjectTarget.realmSet$user(UserRealmProxy.copyOrUpdate(realm, userObj, true, cache));
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
        StringBuilder stringBuilder = new StringBuilder("Session = proxy[");
        stringBuilder.append("{sessionId:");
        stringBuilder.append(realmGet$sessionId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{user:");
        stringBuilder.append(realmGet$user() != null ? "User" : "null");
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
        SessionRealmProxy aSession = (SessionRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aSession.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aSession.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aSession.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
