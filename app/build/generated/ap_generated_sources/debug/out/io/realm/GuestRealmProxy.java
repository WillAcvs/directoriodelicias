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
public class GuestRealmProxy extends com.directoriodelicias.apps.delicias.classes.Guest
    implements RealmObjectProxy, GuestRealmProxyInterface {

    static final class GuestColumnInfo extends ColumnInfo {
        long idIndex;
        long senderIdIndex;
        long fcmIdIndex;
        long latIndex;
        long lngIndex;
        long last_activityIndex;

        GuestColumnInfo(OsSchemaInfo schemaInfo) {
            super(6);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("Guest");
            this.idIndex = addColumnDetails("id", objectSchemaInfo);
            this.senderIdIndex = addColumnDetails("senderId", objectSchemaInfo);
            this.fcmIdIndex = addColumnDetails("fcmId", objectSchemaInfo);
            this.latIndex = addColumnDetails("lat", objectSchemaInfo);
            this.lngIndex = addColumnDetails("lng", objectSchemaInfo);
            this.last_activityIndex = addColumnDetails("last_activity", objectSchemaInfo);
        }

        GuestColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new GuestColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final GuestColumnInfo src = (GuestColumnInfo) rawSrc;
            final GuestColumnInfo dst = (GuestColumnInfo) rawDst;
            dst.idIndex = src.idIndex;
            dst.senderIdIndex = src.senderIdIndex;
            dst.fcmIdIndex = src.fcmIdIndex;
            dst.latIndex = src.latIndex;
            dst.lngIndex = src.lngIndex;
            dst.last_activityIndex = src.last_activityIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(6);
        fieldNames.add("id");
        fieldNames.add("senderId");
        fieldNames.add("fcmId");
        fieldNames.add("lat");
        fieldNames.add("lng");
        fieldNames.add("last_activity");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private GuestColumnInfo columnInfo;
    private ProxyState<com.directoriodelicias.apps.delicias.classes.Guest> proxyState;

    GuestRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (GuestColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.directoriodelicias.apps.delicias.classes.Guest>(this);
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
    public String realmGet$senderId() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.senderIdIndex);
    }

    @Override
    public void realmSet$senderId(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.senderIdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.senderIdIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.senderIdIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.senderIdIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$fcmId() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.fcmIdIndex);
    }

    @Override
    public void realmSet$fcmId(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.fcmIdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.fcmIdIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.fcmIdIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.fcmIdIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public double realmGet$lat() {
        proxyState.getRealm$realm().checkIfValid();
        return (double) proxyState.getRow$realm().getDouble(columnInfo.latIndex);
    }

    @Override
    public void realmSet$lat(double value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setDouble(columnInfo.latIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setDouble(columnInfo.latIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public double realmGet$lng() {
        proxyState.getRealm$realm().checkIfValid();
        return (double) proxyState.getRow$realm().getDouble(columnInfo.lngIndex);
    }

    @Override
    public void realmSet$lng(double value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setDouble(columnInfo.lngIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setDouble(columnInfo.lngIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$last_activity() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.last_activityIndex);
    }

    @Override
    public void realmSet$last_activity(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.last_activityIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.last_activityIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.last_activityIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.last_activityIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("Guest", 6, 0);
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("senderId", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("fcmId", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("lat", RealmFieldType.DOUBLE, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("lng", RealmFieldType.DOUBLE, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("last_activity", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static GuestColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new GuestColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "Guest";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.directoriodelicias.apps.delicias.classes.Guest createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.directoriodelicias.apps.delicias.classes.Guest obj = null;
        if (update) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Guest.class);
            GuestColumnInfo columnInfo = (GuestColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Guest.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Guest.class), false, Collections.<String> emptyList());
                    obj = new io.realm.GuestRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.GuestRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Guest.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.GuestRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Guest.class, json.getInt("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }

        final GuestRealmProxyInterface objProxy = (GuestRealmProxyInterface) obj;
        if (json.has("senderId")) {
            if (json.isNull("senderId")) {
                objProxy.realmSet$senderId(null);
            } else {
                objProxy.realmSet$senderId((String) json.getString("senderId"));
            }
        }
        if (json.has("fcmId")) {
            if (json.isNull("fcmId")) {
                objProxy.realmSet$fcmId(null);
            } else {
                objProxy.realmSet$fcmId((String) json.getString("fcmId"));
            }
        }
        if (json.has("lat")) {
            if (json.isNull("lat")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'lat' to null.");
            } else {
                objProxy.realmSet$lat((double) json.getDouble("lat"));
            }
        }
        if (json.has("lng")) {
            if (json.isNull("lng")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'lng' to null.");
            } else {
                objProxy.realmSet$lng((double) json.getDouble("lng"));
            }
        }
        if (json.has("last_activity")) {
            if (json.isNull("last_activity")) {
                objProxy.realmSet$last_activity(null);
            } else {
                objProxy.realmSet$last_activity((String) json.getString("last_activity"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.directoriodelicias.apps.delicias.classes.Guest createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.directoriodelicias.apps.delicias.classes.Guest obj = new com.directoriodelicias.apps.delicias.classes.Guest();
        final GuestRealmProxyInterface objProxy = (GuestRealmProxyInterface) obj;
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
            } else if (name.equals("senderId")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$senderId((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$senderId(null);
                }
            } else if (name.equals("fcmId")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$fcmId((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$fcmId(null);
                }
            } else if (name.equals("lat")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$lat((double) reader.nextDouble());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'lat' to null.");
                }
            } else if (name.equals("lng")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$lng((double) reader.nextDouble());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'lng' to null.");
                }
            } else if (name.equals("last_activity")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$last_activity((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$last_activity(null);
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

    public static com.directoriodelicias.apps.delicias.classes.Guest copyOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Guest object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.directoriodelicias.apps.delicias.classes.Guest) cachedRealmObject;
        }

        com.directoriodelicias.apps.delicias.classes.Guest realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Guest.class);
            GuestColumnInfo columnInfo = (GuestColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Guest.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((GuestRealmProxyInterface) object).realmGet$id());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Guest.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.GuestRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.directoriodelicias.apps.delicias.classes.Guest copy(Realm realm, com.directoriodelicias.apps.delicias.classes.Guest newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.directoriodelicias.apps.delicias.classes.Guest) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.directoriodelicias.apps.delicias.classes.Guest realmObject = realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Guest.class, ((GuestRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        GuestRealmProxyInterface realmObjectSource = (GuestRealmProxyInterface) newObject;
        GuestRealmProxyInterface realmObjectCopy = (GuestRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$senderId(realmObjectSource.realmGet$senderId());
        realmObjectCopy.realmSet$fcmId(realmObjectSource.realmGet$fcmId());
        realmObjectCopy.realmSet$lat(realmObjectSource.realmGet$lat());
        realmObjectCopy.realmSet$lng(realmObjectSource.realmGet$lng());
        realmObjectCopy.realmSet$last_activity(realmObjectSource.realmGet$last_activity());
        return realmObject;
    }

    public static long insert(Realm realm, com.directoriodelicias.apps.delicias.classes.Guest object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Guest.class);
        long tableNativePtr = table.getNativePtr();
        GuestColumnInfo columnInfo = (GuestColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Guest.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((GuestRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((GuestRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((GuestRealmProxyInterface) object).realmGet$id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$senderId = ((GuestRealmProxyInterface) object).realmGet$senderId();
        if (realmGet$senderId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.senderIdIndex, rowIndex, realmGet$senderId, false);
        }
        String realmGet$fcmId = ((GuestRealmProxyInterface) object).realmGet$fcmId();
        if (realmGet$fcmId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.fcmIdIndex, rowIndex, realmGet$fcmId, false);
        }
        Table.nativeSetDouble(tableNativePtr, columnInfo.latIndex, rowIndex, ((GuestRealmProxyInterface) object).realmGet$lat(), false);
        Table.nativeSetDouble(tableNativePtr, columnInfo.lngIndex, rowIndex, ((GuestRealmProxyInterface) object).realmGet$lng(), false);
        String realmGet$last_activity = ((GuestRealmProxyInterface) object).realmGet$last_activity();
        if (realmGet$last_activity != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.last_activityIndex, rowIndex, realmGet$last_activity, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Guest.class);
        long tableNativePtr = table.getNativePtr();
        GuestColumnInfo columnInfo = (GuestColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Guest.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.Guest object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Guest) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((GuestRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((GuestRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((GuestRealmProxyInterface) object).realmGet$id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$senderId = ((GuestRealmProxyInterface) object).realmGet$senderId();
            if (realmGet$senderId != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.senderIdIndex, rowIndex, realmGet$senderId, false);
            }
            String realmGet$fcmId = ((GuestRealmProxyInterface) object).realmGet$fcmId();
            if (realmGet$fcmId != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.fcmIdIndex, rowIndex, realmGet$fcmId, false);
            }
            Table.nativeSetDouble(tableNativePtr, columnInfo.latIndex, rowIndex, ((GuestRealmProxyInterface) object).realmGet$lat(), false);
            Table.nativeSetDouble(tableNativePtr, columnInfo.lngIndex, rowIndex, ((GuestRealmProxyInterface) object).realmGet$lng(), false);
            String realmGet$last_activity = ((GuestRealmProxyInterface) object).realmGet$last_activity();
            if (realmGet$last_activity != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.last_activityIndex, rowIndex, realmGet$last_activity, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Guest object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Guest.class);
        long tableNativePtr = table.getNativePtr();
        GuestColumnInfo columnInfo = (GuestColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Guest.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((GuestRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((GuestRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((GuestRealmProxyInterface) object).realmGet$id());
        }
        cache.put(object, rowIndex);
        String realmGet$senderId = ((GuestRealmProxyInterface) object).realmGet$senderId();
        if (realmGet$senderId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.senderIdIndex, rowIndex, realmGet$senderId, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.senderIdIndex, rowIndex, false);
        }
        String realmGet$fcmId = ((GuestRealmProxyInterface) object).realmGet$fcmId();
        if (realmGet$fcmId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.fcmIdIndex, rowIndex, realmGet$fcmId, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.fcmIdIndex, rowIndex, false);
        }
        Table.nativeSetDouble(tableNativePtr, columnInfo.latIndex, rowIndex, ((GuestRealmProxyInterface) object).realmGet$lat(), false);
        Table.nativeSetDouble(tableNativePtr, columnInfo.lngIndex, rowIndex, ((GuestRealmProxyInterface) object).realmGet$lng(), false);
        String realmGet$last_activity = ((GuestRealmProxyInterface) object).realmGet$last_activity();
        if (realmGet$last_activity != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.last_activityIndex, rowIndex, realmGet$last_activity, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.last_activityIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Guest.class);
        long tableNativePtr = table.getNativePtr();
        GuestColumnInfo columnInfo = (GuestColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Guest.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.Guest object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Guest) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((GuestRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((GuestRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((GuestRealmProxyInterface) object).realmGet$id());
            }
            cache.put(object, rowIndex);
            String realmGet$senderId = ((GuestRealmProxyInterface) object).realmGet$senderId();
            if (realmGet$senderId != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.senderIdIndex, rowIndex, realmGet$senderId, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.senderIdIndex, rowIndex, false);
            }
            String realmGet$fcmId = ((GuestRealmProxyInterface) object).realmGet$fcmId();
            if (realmGet$fcmId != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.fcmIdIndex, rowIndex, realmGet$fcmId, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.fcmIdIndex, rowIndex, false);
            }
            Table.nativeSetDouble(tableNativePtr, columnInfo.latIndex, rowIndex, ((GuestRealmProxyInterface) object).realmGet$lat(), false);
            Table.nativeSetDouble(tableNativePtr, columnInfo.lngIndex, rowIndex, ((GuestRealmProxyInterface) object).realmGet$lng(), false);
            String realmGet$last_activity = ((GuestRealmProxyInterface) object).realmGet$last_activity();
            if (realmGet$last_activity != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.last_activityIndex, rowIndex, realmGet$last_activity, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.last_activityIndex, rowIndex, false);
            }
        }
    }

    public static com.directoriodelicias.apps.delicias.classes.Guest createDetachedCopy(com.directoriodelicias.apps.delicias.classes.Guest realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.directoriodelicias.apps.delicias.classes.Guest unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.directoriodelicias.apps.delicias.classes.Guest();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.directoriodelicias.apps.delicias.classes.Guest) cachedObject.object;
            }
            unmanagedObject = (com.directoriodelicias.apps.delicias.classes.Guest) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        GuestRealmProxyInterface unmanagedCopy = (GuestRealmProxyInterface) unmanagedObject;
        GuestRealmProxyInterface realmSource = (GuestRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());
        unmanagedCopy.realmSet$senderId(realmSource.realmGet$senderId());
        unmanagedCopy.realmSet$fcmId(realmSource.realmGet$fcmId());
        unmanagedCopy.realmSet$lat(realmSource.realmGet$lat());
        unmanagedCopy.realmSet$lng(realmSource.realmGet$lng());
        unmanagedCopy.realmSet$last_activity(realmSource.realmGet$last_activity());

        return unmanagedObject;
    }

    static com.directoriodelicias.apps.delicias.classes.Guest update(Realm realm, com.directoriodelicias.apps.delicias.classes.Guest realmObject, com.directoriodelicias.apps.delicias.classes.Guest newObject, Map<RealmModel, RealmObjectProxy> cache) {
        GuestRealmProxyInterface realmObjectTarget = (GuestRealmProxyInterface) realmObject;
        GuestRealmProxyInterface realmObjectSource = (GuestRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$senderId(realmObjectSource.realmGet$senderId());
        realmObjectTarget.realmSet$fcmId(realmObjectSource.realmGet$fcmId());
        realmObjectTarget.realmSet$lat(realmObjectSource.realmGet$lat());
        realmObjectTarget.realmSet$lng(realmObjectSource.realmGet$lng());
        realmObjectTarget.realmSet$last_activity(realmObjectSource.realmGet$last_activity());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Guest = proxy[");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{senderId:");
        stringBuilder.append(realmGet$senderId() != null ? realmGet$senderId() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{fcmId:");
        stringBuilder.append(realmGet$fcmId() != null ? realmGet$fcmId() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{lat:");
        stringBuilder.append(realmGet$lat());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{lng:");
        stringBuilder.append(realmGet$lng());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{last_activity:");
        stringBuilder.append(realmGet$last_activity() != null ? realmGet$last_activity() : "null");
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
        GuestRealmProxy aGuest = (GuestRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aGuest.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aGuest.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aGuest.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
