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
public class SavedStoresRealmProxy extends com.directoriodelicias.apps.delicias.classes.SavedStores
    implements RealmObjectProxy, SavedStoresRealmProxyInterface {

    static final class SavedStoresColumnInfo extends ColumnInfo {
        long idIndex;
        long listIDIndex;

        SavedStoresColumnInfo(OsSchemaInfo schemaInfo) {
            super(2);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("SavedStores");
            this.idIndex = addColumnDetails("id", objectSchemaInfo);
            this.listIDIndex = addColumnDetails("listID", objectSchemaInfo);
        }

        SavedStoresColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new SavedStoresColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final SavedStoresColumnInfo src = (SavedStoresColumnInfo) rawSrc;
            final SavedStoresColumnInfo dst = (SavedStoresColumnInfo) rawDst;
            dst.idIndex = src.idIndex;
            dst.listIDIndex = src.listIDIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(2);
        fieldNames.add("id");
        fieldNames.add("listID");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private SavedStoresColumnInfo columnInfo;
    private ProxyState<com.directoriodelicias.apps.delicias.classes.SavedStores> proxyState;
    private RealmList<Integer> listIDRealmList;

    SavedStoresRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (SavedStoresColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.directoriodelicias.apps.delicias.classes.SavedStores>(this);
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
    public RealmList<Integer> realmGet$listID() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (listIDRealmList != null) {
            return listIDRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getValueList(columnInfo.listIDIndex, RealmFieldType.INTEGER_LIST);
            listIDRealmList = new RealmList<java.lang.Integer>(java.lang.Integer.class, osList, proxyState.getRealm$realm());
            return listIDRealmList;
        }
    }

    @Override
    public void realmSet$listID(RealmList<Integer> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("listID")) {
                return;
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getValueList(columnInfo.listIDIndex, RealmFieldType.INTEGER_LIST);
        osList.removeAll();
        if (value == null) {
            return;
        }
        for (java.lang.Integer item : value) {
            if (item == null) {
                osList.addNull();
            } else {
                osList.addLong(item.longValue());
            }
        }
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("SavedStores", 2, 0);
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedValueListProperty("listID", RealmFieldType.INTEGER_LIST, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static SavedStoresColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new SavedStoresColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "SavedStores";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.directoriodelicias.apps.delicias.classes.SavedStores createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(1);
        com.directoriodelicias.apps.delicias.classes.SavedStores obj = null;
        if (update) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.SavedStores.class);
            SavedStoresColumnInfo columnInfo = (SavedStoresColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.SavedStores.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.SavedStores.class), false, Collections.<String> emptyList());
                    obj = new io.realm.SavedStoresRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("listID")) {
                excludeFields.add("listID");
            }
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.SavedStoresRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.SavedStores.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.SavedStoresRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.SavedStores.class, json.getInt("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }

        final SavedStoresRealmProxyInterface objProxy = (SavedStoresRealmProxyInterface) obj;
        // TODO implement logic for value list listID.
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.directoriodelicias.apps.delicias.classes.SavedStores createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.directoriodelicias.apps.delicias.classes.SavedStores obj = new com.directoriodelicias.apps.delicias.classes.SavedStores();
        final SavedStoresRealmProxyInterface objProxy = (SavedStoresRealmProxyInterface) obj;
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
            } else if (name.equals("listID")) {
                // TODO implement logic for value list.
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

    public static com.directoriodelicias.apps.delicias.classes.SavedStores copyOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.SavedStores object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.directoriodelicias.apps.delicias.classes.SavedStores) cachedRealmObject;
        }

        com.directoriodelicias.apps.delicias.classes.SavedStores realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.SavedStores.class);
            SavedStoresColumnInfo columnInfo = (SavedStoresColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.SavedStores.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((SavedStoresRealmProxyInterface) object).realmGet$id());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.SavedStores.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.SavedStoresRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.directoriodelicias.apps.delicias.classes.SavedStores copy(Realm realm, com.directoriodelicias.apps.delicias.classes.SavedStores newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.directoriodelicias.apps.delicias.classes.SavedStores) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.directoriodelicias.apps.delicias.classes.SavedStores realmObject = realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.SavedStores.class, ((SavedStoresRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        SavedStoresRealmProxyInterface realmObjectSource = (SavedStoresRealmProxyInterface) newObject;
        SavedStoresRealmProxyInterface realmObjectCopy = (SavedStoresRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$listID(realmObjectSource.realmGet$listID());
        return realmObject;
    }

    public static long insert(Realm realm, com.directoriodelicias.apps.delicias.classes.SavedStores object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.SavedStores.class);
        long tableNativePtr = table.getNativePtr();
        SavedStoresColumnInfo columnInfo = (SavedStoresColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.SavedStores.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((SavedStoresRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((SavedStoresRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((SavedStoresRealmProxyInterface) object).realmGet$id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);

        RealmList<java.lang.Integer> listIDList = ((SavedStoresRealmProxyInterface) object).realmGet$listID();
        if (listIDList != null) {
            OsList listIDOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.listIDIndex);
            for (java.lang.Integer listIDItem : listIDList) {
                if (listIDItem == null) {
                    listIDOsList.addNull();
                } else {
                    listIDOsList.addLong(listIDItem.longValue());
                }
            }
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.SavedStores.class);
        long tableNativePtr = table.getNativePtr();
        SavedStoresColumnInfo columnInfo = (SavedStoresColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.SavedStores.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.SavedStores object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.SavedStores) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((SavedStoresRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((SavedStoresRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((SavedStoresRealmProxyInterface) object).realmGet$id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);

            RealmList<java.lang.Integer> listIDList = ((SavedStoresRealmProxyInterface) object).realmGet$listID();
            if (listIDList != null) {
                OsList listIDOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.listIDIndex);
                for (java.lang.Integer listIDItem : listIDList) {
                    if (listIDItem == null) {
                        listIDOsList.addNull();
                    } else {
                        listIDOsList.addLong(listIDItem.longValue());
                    }
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.SavedStores object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.SavedStores.class);
        long tableNativePtr = table.getNativePtr();
        SavedStoresColumnInfo columnInfo = (SavedStoresColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.SavedStores.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((SavedStoresRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((SavedStoresRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((SavedStoresRealmProxyInterface) object).realmGet$id());
        }
        cache.put(object, rowIndex);

        OsList listIDOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.listIDIndex);
        listIDOsList.removeAll();
        RealmList<java.lang.Integer> listIDList = ((SavedStoresRealmProxyInterface) object).realmGet$listID();
        if (listIDList != null) {
            for (java.lang.Integer listIDItem : listIDList) {
                if (listIDItem == null) {
                    listIDOsList.addNull();
                } else {
                    listIDOsList.addLong(listIDItem.longValue());
                }
            }
        }

        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.SavedStores.class);
        long tableNativePtr = table.getNativePtr();
        SavedStoresColumnInfo columnInfo = (SavedStoresColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.SavedStores.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.SavedStores object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.SavedStores) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((SavedStoresRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((SavedStoresRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((SavedStoresRealmProxyInterface) object).realmGet$id());
            }
            cache.put(object, rowIndex);

            OsList listIDOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.listIDIndex);
            listIDOsList.removeAll();
            RealmList<java.lang.Integer> listIDList = ((SavedStoresRealmProxyInterface) object).realmGet$listID();
            if (listIDList != null) {
                for (java.lang.Integer listIDItem : listIDList) {
                    if (listIDItem == null) {
                        listIDOsList.addNull();
                    } else {
                        listIDOsList.addLong(listIDItem.longValue());
                    }
                }
            }

        }
    }

    public static com.directoriodelicias.apps.delicias.classes.SavedStores createDetachedCopy(com.directoriodelicias.apps.delicias.classes.SavedStores realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.directoriodelicias.apps.delicias.classes.SavedStores unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.directoriodelicias.apps.delicias.classes.SavedStores();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.directoriodelicias.apps.delicias.classes.SavedStores) cachedObject.object;
            }
            unmanagedObject = (com.directoriodelicias.apps.delicias.classes.SavedStores) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        SavedStoresRealmProxyInterface unmanagedCopy = (SavedStoresRealmProxyInterface) unmanagedObject;
        SavedStoresRealmProxyInterface realmSource = (SavedStoresRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());

        unmanagedCopy.realmSet$listID(new RealmList<java.lang.Integer>());
        unmanagedCopy.realmGet$listID().addAll(realmSource.realmGet$listID());

        return unmanagedObject;
    }

    static com.directoriodelicias.apps.delicias.classes.SavedStores update(Realm realm, com.directoriodelicias.apps.delicias.classes.SavedStores realmObject, com.directoriodelicias.apps.delicias.classes.SavedStores newObject, Map<RealmModel, RealmObjectProxy> cache) {
        SavedStoresRealmProxyInterface realmObjectTarget = (SavedStoresRealmProxyInterface) realmObject;
        SavedStoresRealmProxyInterface realmObjectSource = (SavedStoresRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$listID(realmObjectSource.realmGet$listID());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("SavedStores = proxy[");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{listID:");
        stringBuilder.append("RealmList<Integer>[").append(realmGet$listID().size()).append("]");
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
        SavedStoresRealmProxy aSavedStores = (SavedStoresRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aSavedStores.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aSavedStores.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aSavedStores.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
