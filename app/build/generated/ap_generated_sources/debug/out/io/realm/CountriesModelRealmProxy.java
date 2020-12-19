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
public class CountriesModelRealmProxy extends com.directoriodelicias.apps.delicias.classes.CountriesModel
    implements RealmObjectProxy, CountriesModelRealmProxyInterface {

    static final class CountriesModelColumnInfo extends ColumnInfo {
        long nameIndex;
        long dial_codeIndex;
        long codeIndex;

        CountriesModelColumnInfo(OsSchemaInfo schemaInfo) {
            super(3);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("CountriesModel");
            this.nameIndex = addColumnDetails("name", objectSchemaInfo);
            this.dial_codeIndex = addColumnDetails("dial_code", objectSchemaInfo);
            this.codeIndex = addColumnDetails("code", objectSchemaInfo);
        }

        CountriesModelColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new CountriesModelColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final CountriesModelColumnInfo src = (CountriesModelColumnInfo) rawSrc;
            final CountriesModelColumnInfo dst = (CountriesModelColumnInfo) rawDst;
            dst.nameIndex = src.nameIndex;
            dst.dial_codeIndex = src.dial_codeIndex;
            dst.codeIndex = src.codeIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(3);
        fieldNames.add("name");
        fieldNames.add("dial_code");
        fieldNames.add("code");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private CountriesModelColumnInfo columnInfo;
    private ProxyState<com.directoriodelicias.apps.delicias.classes.CountriesModel> proxyState;

    CountriesModelRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (CountriesModelColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.directoriodelicias.apps.delicias.classes.CountriesModel>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
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
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'name' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$dial_code() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.dial_codeIndex);
    }

    @Override
    public void realmSet$dial_code(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.dial_codeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.dial_codeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.dial_codeIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.dial_codeIndex, value);
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

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("CountriesModel", 3, 0);
        builder.addPersistedProperty("name", RealmFieldType.STRING, Property.PRIMARY_KEY, Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("dial_code", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("code", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static CountriesModelColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new CountriesModelColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "CountriesModel";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.directoriodelicias.apps.delicias.classes.CountriesModel createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.directoriodelicias.apps.delicias.classes.CountriesModel obj = null;
        if (update) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.CountriesModel.class);
            CountriesModelColumnInfo columnInfo = (CountriesModelColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.CountriesModel.class);
            long pkColumnIndex = columnInfo.nameIndex;
            long rowIndex = Table.NO_MATCH;
            if (json.isNull("name")) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, json.getString("name"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.CountriesModel.class), false, Collections.<String> emptyList());
                    obj = new io.realm.CountriesModelRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("name")) {
                if (json.isNull("name")) {
                    obj = (io.realm.CountriesModelRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.CountriesModel.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.CountriesModelRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.CountriesModel.class, json.getString("name"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'name'.");
            }
        }

        final CountriesModelRealmProxyInterface objProxy = (CountriesModelRealmProxyInterface) obj;
        if (json.has("dial_code")) {
            if (json.isNull("dial_code")) {
                objProxy.realmSet$dial_code(null);
            } else {
                objProxy.realmSet$dial_code((String) json.getString("dial_code"));
            }
        }
        if (json.has("code")) {
            if (json.isNull("code")) {
                objProxy.realmSet$code(null);
            } else {
                objProxy.realmSet$code((String) json.getString("code"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.directoriodelicias.apps.delicias.classes.CountriesModel createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.directoriodelicias.apps.delicias.classes.CountriesModel obj = new com.directoriodelicias.apps.delicias.classes.CountriesModel();
        final CountriesModelRealmProxyInterface objProxy = (CountriesModelRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$name(null);
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("dial_code")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$dial_code((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$dial_code(null);
                }
            } else if (name.equals("code")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$code((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$code(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'name'.");
        }
        return realm.copyToRealm(obj);
    }

    public static com.directoriodelicias.apps.delicias.classes.CountriesModel copyOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.CountriesModel object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.directoriodelicias.apps.delicias.classes.CountriesModel) cachedRealmObject;
        }

        com.directoriodelicias.apps.delicias.classes.CountriesModel realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.CountriesModel.class);
            CountriesModelColumnInfo columnInfo = (CountriesModelColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.CountriesModel.class);
            long pkColumnIndex = columnInfo.nameIndex;
            String value = ((CountriesModelRealmProxyInterface) object).realmGet$name();
            long rowIndex = Table.NO_MATCH;
            if (value == null) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, value);
            }
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.CountriesModel.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.CountriesModelRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.directoriodelicias.apps.delicias.classes.CountriesModel copy(Realm realm, com.directoriodelicias.apps.delicias.classes.CountriesModel newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.directoriodelicias.apps.delicias.classes.CountriesModel) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.directoriodelicias.apps.delicias.classes.CountriesModel realmObject = realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.CountriesModel.class, ((CountriesModelRealmProxyInterface) newObject).realmGet$name(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        CountriesModelRealmProxyInterface realmObjectSource = (CountriesModelRealmProxyInterface) newObject;
        CountriesModelRealmProxyInterface realmObjectCopy = (CountriesModelRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$dial_code(realmObjectSource.realmGet$dial_code());
        realmObjectCopy.realmSet$code(realmObjectSource.realmGet$code());
        return realmObject;
    }

    public static long insert(Realm realm, com.directoriodelicias.apps.delicias.classes.CountriesModel object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.CountriesModel.class);
        long tableNativePtr = table.getNativePtr();
        CountriesModelColumnInfo columnInfo = (CountriesModelColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.CountriesModel.class);
        long pkColumnIndex = columnInfo.nameIndex;
        String primaryKeyValue = ((CountriesModelRealmProxyInterface) object).realmGet$name();
        long rowIndex = Table.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, primaryKeyValue);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$dial_code = ((CountriesModelRealmProxyInterface) object).realmGet$dial_code();
        if (realmGet$dial_code != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dial_codeIndex, rowIndex, realmGet$dial_code, false);
        }
        String realmGet$code = ((CountriesModelRealmProxyInterface) object).realmGet$code();
        if (realmGet$code != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.codeIndex, rowIndex, realmGet$code, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.CountriesModel.class);
        long tableNativePtr = table.getNativePtr();
        CountriesModelColumnInfo columnInfo = (CountriesModelColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.CountriesModel.class);
        long pkColumnIndex = columnInfo.nameIndex;
        com.directoriodelicias.apps.delicias.classes.CountriesModel object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.CountriesModel) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            String primaryKeyValue = ((CountriesModelRealmProxyInterface) object).realmGet$name();
            long rowIndex = Table.NO_MATCH;
            if (primaryKeyValue == null) {
                rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
            } else {
                rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, primaryKeyValue);
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$dial_code = ((CountriesModelRealmProxyInterface) object).realmGet$dial_code();
            if (realmGet$dial_code != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dial_codeIndex, rowIndex, realmGet$dial_code, false);
            }
            String realmGet$code = ((CountriesModelRealmProxyInterface) object).realmGet$code();
            if (realmGet$code != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.codeIndex, rowIndex, realmGet$code, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.CountriesModel object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.CountriesModel.class);
        long tableNativePtr = table.getNativePtr();
        CountriesModelColumnInfo columnInfo = (CountriesModelColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.CountriesModel.class);
        long pkColumnIndex = columnInfo.nameIndex;
        String primaryKeyValue = ((CountriesModelRealmProxyInterface) object).realmGet$name();
        long rowIndex = Table.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$dial_code = ((CountriesModelRealmProxyInterface) object).realmGet$dial_code();
        if (realmGet$dial_code != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dial_codeIndex, rowIndex, realmGet$dial_code, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dial_codeIndex, rowIndex, false);
        }
        String realmGet$code = ((CountriesModelRealmProxyInterface) object).realmGet$code();
        if (realmGet$code != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.codeIndex, rowIndex, realmGet$code, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.codeIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.CountriesModel.class);
        long tableNativePtr = table.getNativePtr();
        CountriesModelColumnInfo columnInfo = (CountriesModelColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.CountriesModel.class);
        long pkColumnIndex = columnInfo.nameIndex;
        com.directoriodelicias.apps.delicias.classes.CountriesModel object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.CountriesModel) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            String primaryKeyValue = ((CountriesModelRealmProxyInterface) object).realmGet$name();
            long rowIndex = Table.NO_MATCH;
            if (primaryKeyValue == null) {
                rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
            } else {
                rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$dial_code = ((CountriesModelRealmProxyInterface) object).realmGet$dial_code();
            if (realmGet$dial_code != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dial_codeIndex, rowIndex, realmGet$dial_code, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dial_codeIndex, rowIndex, false);
            }
            String realmGet$code = ((CountriesModelRealmProxyInterface) object).realmGet$code();
            if (realmGet$code != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.codeIndex, rowIndex, realmGet$code, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.codeIndex, rowIndex, false);
            }
        }
    }

    public static com.directoriodelicias.apps.delicias.classes.CountriesModel createDetachedCopy(com.directoriodelicias.apps.delicias.classes.CountriesModel realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.directoriodelicias.apps.delicias.classes.CountriesModel unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.directoriodelicias.apps.delicias.classes.CountriesModel();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.directoriodelicias.apps.delicias.classes.CountriesModel) cachedObject.object;
            }
            unmanagedObject = (com.directoriodelicias.apps.delicias.classes.CountriesModel) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        CountriesModelRealmProxyInterface unmanagedCopy = (CountriesModelRealmProxyInterface) unmanagedObject;
        CountriesModelRealmProxyInterface realmSource = (CountriesModelRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$name(realmSource.realmGet$name());
        unmanagedCopy.realmSet$dial_code(realmSource.realmGet$dial_code());
        unmanagedCopy.realmSet$code(realmSource.realmGet$code());

        return unmanagedObject;
    }

    static com.directoriodelicias.apps.delicias.classes.CountriesModel update(Realm realm, com.directoriodelicias.apps.delicias.classes.CountriesModel realmObject, com.directoriodelicias.apps.delicias.classes.CountriesModel newObject, Map<RealmModel, RealmObjectProxy> cache) {
        CountriesModelRealmProxyInterface realmObjectTarget = (CountriesModelRealmProxyInterface) realmObject;
        CountriesModelRealmProxyInterface realmObjectSource = (CountriesModelRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$dial_code(realmObjectSource.realmGet$dial_code());
        realmObjectTarget.realmSet$code(realmObjectSource.realmGet$code());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("CountriesModel = proxy[");
        stringBuilder.append("{name:");
        stringBuilder.append(realmGet$name() != null ? realmGet$name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{dial_code:");
        stringBuilder.append(realmGet$dial_code() != null ? realmGet$dial_code() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{code:");
        stringBuilder.append(realmGet$code() != null ? realmGet$code() : "null");
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
        CountriesModelRealmProxy aCountriesModel = (CountriesModelRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aCountriesModel.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aCountriesModel.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aCountriesModel.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
