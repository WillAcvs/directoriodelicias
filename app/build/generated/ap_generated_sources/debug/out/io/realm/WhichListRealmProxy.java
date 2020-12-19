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
public class WhichListRealmProxy extends com.directoriodelicias.apps.delicias.classes.WhichList
    implements RealmObjectProxy, WhichListRealmProxyInterface {

    static final class WhichListColumnInfo extends ColumnInfo {
        long idIndex;
        long listItemsIndex;

        WhichListColumnInfo(OsSchemaInfo schemaInfo) {
            super(2);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("WhichList");
            this.idIndex = addColumnDetails("id", objectSchemaInfo);
            this.listItemsIndex = addColumnDetails("listItems", objectSchemaInfo);
        }

        WhichListColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new WhichListColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final WhichListColumnInfo src = (WhichListColumnInfo) rawSrc;
            final WhichListColumnInfo dst = (WhichListColumnInfo) rawDst;
            dst.idIndex = src.idIndex;
            dst.listItemsIndex = src.listItemsIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(2);
        fieldNames.add("id");
        fieldNames.add("listItems");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private WhichListColumnInfo columnInfo;
    private ProxyState<com.directoriodelicias.apps.delicias.classes.WhichList> proxyState;
    private RealmList<com.directoriodelicias.apps.delicias.classes.Bookmark> listItemsRealmList;

    WhichListRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (WhichListColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.directoriodelicias.apps.delicias.classes.WhichList>(this);
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
    public RealmList<com.directoriodelicias.apps.delicias.classes.Bookmark> realmGet$listItems() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (listItemsRealmList != null) {
            return listItemsRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.listItemsIndex);
            listItemsRealmList = new RealmList<com.directoriodelicias.apps.delicias.classes.Bookmark>(com.directoriodelicias.apps.delicias.classes.Bookmark.class, osList, proxyState.getRealm$realm());
            return listItemsRealmList;
        }
    }

    @Override
    public void realmSet$listItems(RealmList<com.directoriodelicias.apps.delicias.classes.Bookmark> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("listItems")) {
                return;
            }
            // if the list contains unmanaged RealmObjects, convert them to managed.
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.directoriodelicias.apps.delicias.classes.Bookmark> original = value;
                value = new RealmList<com.directoriodelicias.apps.delicias.classes.Bookmark>();
                for (com.directoriodelicias.apps.delicias.classes.Bookmark item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.listItemsIndex);
        // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
        if (value != null && value.size() == osList.size()) {
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.directoriodelicias.apps.delicias.classes.Bookmark linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.setRow(i, ((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        } else {
            osList.removeAll();
            if (value == null) {
                return;
            }
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.directoriodelicias.apps.delicias.classes.Bookmark linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.addRow(((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        }
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("WhichList", 2, 0);
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedLinkProperty("listItems", RealmFieldType.LIST, "Bookmark");
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static WhichListColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new WhichListColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "WhichList";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.directoriodelicias.apps.delicias.classes.WhichList createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(1);
        com.directoriodelicias.apps.delicias.classes.WhichList obj = null;
        if (update) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.WhichList.class);
            WhichListColumnInfo columnInfo = (WhichListColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.WhichList.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.WhichList.class), false, Collections.<String> emptyList());
                    obj = new io.realm.WhichListRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("listItems")) {
                excludeFields.add("listItems");
            }
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.WhichListRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.WhichList.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.WhichListRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.WhichList.class, json.getInt("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }

        final WhichListRealmProxyInterface objProxy = (WhichListRealmProxyInterface) obj;
        if (json.has("listItems")) {
            if (json.isNull("listItems")) {
                objProxy.realmSet$listItems(null);
            } else {
                objProxy.realmGet$listItems().clear();
                JSONArray array = json.getJSONArray("listItems");
                for (int i = 0; i < array.length(); i++) {
                    com.directoriodelicias.apps.delicias.classes.Bookmark item = BookmarkRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$listItems().add(item);
                }
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.directoriodelicias.apps.delicias.classes.WhichList createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.directoriodelicias.apps.delicias.classes.WhichList obj = new com.directoriodelicias.apps.delicias.classes.WhichList();
        final WhichListRealmProxyInterface objProxy = (WhichListRealmProxyInterface) obj;
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
            } else if (name.equals("listItems")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$listItems(null);
                } else {
                    objProxy.realmSet$listItems(new RealmList<com.directoriodelicias.apps.delicias.classes.Bookmark>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.directoriodelicias.apps.delicias.classes.Bookmark item = BookmarkRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$listItems().add(item);
                    }
                    reader.endArray();
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

    public static com.directoriodelicias.apps.delicias.classes.WhichList copyOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.WhichList object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.directoriodelicias.apps.delicias.classes.WhichList) cachedRealmObject;
        }

        com.directoriodelicias.apps.delicias.classes.WhichList realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.WhichList.class);
            WhichListColumnInfo columnInfo = (WhichListColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.WhichList.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((WhichListRealmProxyInterface) object).realmGet$id());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.WhichList.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.WhichListRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.directoriodelicias.apps.delicias.classes.WhichList copy(Realm realm, com.directoriodelicias.apps.delicias.classes.WhichList newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.directoriodelicias.apps.delicias.classes.WhichList) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.directoriodelicias.apps.delicias.classes.WhichList realmObject = realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.WhichList.class, ((WhichListRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        WhichListRealmProxyInterface realmObjectSource = (WhichListRealmProxyInterface) newObject;
        WhichListRealmProxyInterface realmObjectCopy = (WhichListRealmProxyInterface) realmObject;


        RealmList<com.directoriodelicias.apps.delicias.classes.Bookmark> listItemsList = realmObjectSource.realmGet$listItems();
        if (listItemsList != null) {
            RealmList<com.directoriodelicias.apps.delicias.classes.Bookmark> listItemsRealmList = realmObjectCopy.realmGet$listItems();
            listItemsRealmList.clear();
            for (int i = 0; i < listItemsList.size(); i++) {
                com.directoriodelicias.apps.delicias.classes.Bookmark listItemsItem = listItemsList.get(i);
                com.directoriodelicias.apps.delicias.classes.Bookmark cachelistItems = (com.directoriodelicias.apps.delicias.classes.Bookmark) cache.get(listItemsItem);
                if (cachelistItems != null) {
                    listItemsRealmList.add(cachelistItems);
                } else {
                    listItemsRealmList.add(BookmarkRealmProxy.copyOrUpdate(realm, listItemsItem, update, cache));
                }
            }
        }

        return realmObject;
    }

    public static long insert(Realm realm, com.directoriodelicias.apps.delicias.classes.WhichList object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.WhichList.class);
        long tableNativePtr = table.getNativePtr();
        WhichListColumnInfo columnInfo = (WhichListColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.WhichList.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((WhichListRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((WhichListRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((WhichListRealmProxyInterface) object).realmGet$id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);

        RealmList<com.directoriodelicias.apps.delicias.classes.Bookmark> listItemsList = ((WhichListRealmProxyInterface) object).realmGet$listItems();
        if (listItemsList != null) {
            OsList listItemsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.listItemsIndex);
            for (com.directoriodelicias.apps.delicias.classes.Bookmark listItemsItem : listItemsList) {
                Long cacheItemIndexlistItems = cache.get(listItemsItem);
                if (cacheItemIndexlistItems == null) {
                    cacheItemIndexlistItems = BookmarkRealmProxy.insert(realm, listItemsItem, cache);
                }
                listItemsOsList.addRow(cacheItemIndexlistItems);
            }
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.WhichList.class);
        long tableNativePtr = table.getNativePtr();
        WhichListColumnInfo columnInfo = (WhichListColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.WhichList.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.WhichList object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.WhichList) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((WhichListRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((WhichListRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((WhichListRealmProxyInterface) object).realmGet$id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);

            RealmList<com.directoriodelicias.apps.delicias.classes.Bookmark> listItemsList = ((WhichListRealmProxyInterface) object).realmGet$listItems();
            if (listItemsList != null) {
                OsList listItemsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.listItemsIndex);
                for (com.directoriodelicias.apps.delicias.classes.Bookmark listItemsItem : listItemsList) {
                    Long cacheItemIndexlistItems = cache.get(listItemsItem);
                    if (cacheItemIndexlistItems == null) {
                        cacheItemIndexlistItems = BookmarkRealmProxy.insert(realm, listItemsItem, cache);
                    }
                    listItemsOsList.addRow(cacheItemIndexlistItems);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.WhichList object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.WhichList.class);
        long tableNativePtr = table.getNativePtr();
        WhichListColumnInfo columnInfo = (WhichListColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.WhichList.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((WhichListRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((WhichListRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((WhichListRealmProxyInterface) object).realmGet$id());
        }
        cache.put(object, rowIndex);

        OsList listItemsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.listItemsIndex);
        RealmList<com.directoriodelicias.apps.delicias.classes.Bookmark> listItemsList = ((WhichListRealmProxyInterface) object).realmGet$listItems();
        if (listItemsList != null && listItemsList.size() == listItemsOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = listItemsList.size();
            for (int i = 0; i < objects; i++) {
                com.directoriodelicias.apps.delicias.classes.Bookmark listItemsItem = listItemsList.get(i);
                Long cacheItemIndexlistItems = cache.get(listItemsItem);
                if (cacheItemIndexlistItems == null) {
                    cacheItemIndexlistItems = BookmarkRealmProxy.insertOrUpdate(realm, listItemsItem, cache);
                }
                listItemsOsList.setRow(i, cacheItemIndexlistItems);
            }
        } else {
            listItemsOsList.removeAll();
            if (listItemsList != null) {
                for (com.directoriodelicias.apps.delicias.classes.Bookmark listItemsItem : listItemsList) {
                    Long cacheItemIndexlistItems = cache.get(listItemsItem);
                    if (cacheItemIndexlistItems == null) {
                        cacheItemIndexlistItems = BookmarkRealmProxy.insertOrUpdate(realm, listItemsItem, cache);
                    }
                    listItemsOsList.addRow(cacheItemIndexlistItems);
                }
            }
        }

        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.WhichList.class);
        long tableNativePtr = table.getNativePtr();
        WhichListColumnInfo columnInfo = (WhichListColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.WhichList.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.WhichList object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.WhichList) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((WhichListRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((WhichListRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((WhichListRealmProxyInterface) object).realmGet$id());
            }
            cache.put(object, rowIndex);

            OsList listItemsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.listItemsIndex);
            RealmList<com.directoriodelicias.apps.delicias.classes.Bookmark> listItemsList = ((WhichListRealmProxyInterface) object).realmGet$listItems();
            if (listItemsList != null && listItemsList.size() == listItemsOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = listItemsList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.directoriodelicias.apps.delicias.classes.Bookmark listItemsItem = listItemsList.get(i);
                    Long cacheItemIndexlistItems = cache.get(listItemsItem);
                    if (cacheItemIndexlistItems == null) {
                        cacheItemIndexlistItems = BookmarkRealmProxy.insertOrUpdate(realm, listItemsItem, cache);
                    }
                    listItemsOsList.setRow(i, cacheItemIndexlistItems);
                }
            } else {
                listItemsOsList.removeAll();
                if (listItemsList != null) {
                    for (com.directoriodelicias.apps.delicias.classes.Bookmark listItemsItem : listItemsList) {
                        Long cacheItemIndexlistItems = cache.get(listItemsItem);
                        if (cacheItemIndexlistItems == null) {
                            cacheItemIndexlistItems = BookmarkRealmProxy.insertOrUpdate(realm, listItemsItem, cache);
                        }
                        listItemsOsList.addRow(cacheItemIndexlistItems);
                    }
                }
            }

        }
    }

    public static com.directoriodelicias.apps.delicias.classes.WhichList createDetachedCopy(com.directoriodelicias.apps.delicias.classes.WhichList realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.directoriodelicias.apps.delicias.classes.WhichList unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.directoriodelicias.apps.delicias.classes.WhichList();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.directoriodelicias.apps.delicias.classes.WhichList) cachedObject.object;
            }
            unmanagedObject = (com.directoriodelicias.apps.delicias.classes.WhichList) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        WhichListRealmProxyInterface unmanagedCopy = (WhichListRealmProxyInterface) unmanagedObject;
        WhichListRealmProxyInterface realmSource = (WhichListRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());

        // Deep copy of listItems
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$listItems(null);
        } else {
            RealmList<com.directoriodelicias.apps.delicias.classes.Bookmark> managedlistItemsList = realmSource.realmGet$listItems();
            RealmList<com.directoriodelicias.apps.delicias.classes.Bookmark> unmanagedlistItemsList = new RealmList<com.directoriodelicias.apps.delicias.classes.Bookmark>();
            unmanagedCopy.realmSet$listItems(unmanagedlistItemsList);
            int nextDepth = currentDepth + 1;
            int size = managedlistItemsList.size();
            for (int i = 0; i < size; i++) {
                com.directoriodelicias.apps.delicias.classes.Bookmark item = BookmarkRealmProxy.createDetachedCopy(managedlistItemsList.get(i), nextDepth, maxDepth, cache);
                unmanagedlistItemsList.add(item);
            }
        }

        return unmanagedObject;
    }

    static com.directoriodelicias.apps.delicias.classes.WhichList update(Realm realm, com.directoriodelicias.apps.delicias.classes.WhichList realmObject, com.directoriodelicias.apps.delicias.classes.WhichList newObject, Map<RealmModel, RealmObjectProxy> cache) {
        WhichListRealmProxyInterface realmObjectTarget = (WhichListRealmProxyInterface) realmObject;
        WhichListRealmProxyInterface realmObjectSource = (WhichListRealmProxyInterface) newObject;
        RealmList<com.directoriodelicias.apps.delicias.classes.Bookmark> listItemsList = realmObjectSource.realmGet$listItems();
        RealmList<com.directoriodelicias.apps.delicias.classes.Bookmark> listItemsRealmList = realmObjectTarget.realmGet$listItems();
        if (listItemsList != null && listItemsList.size() == listItemsRealmList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = listItemsList.size();
            for (int i = 0; i < objects; i++) {
                com.directoriodelicias.apps.delicias.classes.Bookmark listItemsItem = listItemsList.get(i);
                com.directoriodelicias.apps.delicias.classes.Bookmark cachelistItems = (com.directoriodelicias.apps.delicias.classes.Bookmark) cache.get(listItemsItem);
                if (cachelistItems != null) {
                    listItemsRealmList.set(i, cachelistItems);
                } else {
                    listItemsRealmList.set(i, BookmarkRealmProxy.copyOrUpdate(realm, listItemsItem, true, cache));
                }
            }
        } else {
            listItemsRealmList.clear();
            if (listItemsList != null) {
                for (int i = 0; i < listItemsList.size(); i++) {
                    com.directoriodelicias.apps.delicias.classes.Bookmark listItemsItem = listItemsList.get(i);
                    com.directoriodelicias.apps.delicias.classes.Bookmark cachelistItems = (com.directoriodelicias.apps.delicias.classes.Bookmark) cache.get(listItemsItem);
                    if (cachelistItems != null) {
                        listItemsRealmList.add(cachelistItems);
                    } else {
                        listItemsRealmList.add(BookmarkRealmProxy.copyOrUpdate(realm, listItemsItem, true, cache));
                    }
                }
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
        StringBuilder stringBuilder = new StringBuilder("WhichList = proxy[");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{listItems:");
        stringBuilder.append("RealmList<Bookmark>[").append(realmGet$listItems().size()).append("]");
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
        WhichListRealmProxy aWhichList = (WhichListRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aWhichList.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aWhichList.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aWhichList.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
