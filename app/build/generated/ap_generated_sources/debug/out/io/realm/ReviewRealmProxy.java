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
public class ReviewRealmProxy extends com.directoriodelicias.apps.delicias.classes.Review
    implements RealmObjectProxy, ReviewRealmProxyInterface {

    static final class ReviewColumnInfo extends ColumnInfo {
        long id_rateIndex;
        long store_idIndex;
        long rateIndex;
        long reviewIndex;
        long pseudoIndex;
        long imageIndex;
        long guest_idIndex;

        ReviewColumnInfo(OsSchemaInfo schemaInfo) {
            super(7);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("Review");
            this.id_rateIndex = addColumnDetails("id_rate", objectSchemaInfo);
            this.store_idIndex = addColumnDetails("store_id", objectSchemaInfo);
            this.rateIndex = addColumnDetails("rate", objectSchemaInfo);
            this.reviewIndex = addColumnDetails("review", objectSchemaInfo);
            this.pseudoIndex = addColumnDetails("pseudo", objectSchemaInfo);
            this.imageIndex = addColumnDetails("image", objectSchemaInfo);
            this.guest_idIndex = addColumnDetails("guest_id", objectSchemaInfo);
        }

        ReviewColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new ReviewColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final ReviewColumnInfo src = (ReviewColumnInfo) rawSrc;
            final ReviewColumnInfo dst = (ReviewColumnInfo) rawDst;
            dst.id_rateIndex = src.id_rateIndex;
            dst.store_idIndex = src.store_idIndex;
            dst.rateIndex = src.rateIndex;
            dst.reviewIndex = src.reviewIndex;
            dst.pseudoIndex = src.pseudoIndex;
            dst.imageIndex = src.imageIndex;
            dst.guest_idIndex = src.guest_idIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(7);
        fieldNames.add("id_rate");
        fieldNames.add("store_id");
        fieldNames.add("rate");
        fieldNames.add("review");
        fieldNames.add("pseudo");
        fieldNames.add("image");
        fieldNames.add("guest_id");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private ReviewColumnInfo columnInfo;
    private ProxyState<com.directoriodelicias.apps.delicias.classes.Review> proxyState;

    ReviewRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (ReviewColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.directoriodelicias.apps.delicias.classes.Review>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$id_rate() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.id_rateIndex);
    }

    @Override
    public void realmSet$id_rate(int value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'id_rate' cannot be changed after object was created.");
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
    public double realmGet$rate() {
        proxyState.getRealm$realm().checkIfValid();
        return (double) proxyState.getRow$realm().getDouble(columnInfo.rateIndex);
    }

    @Override
    public void realmSet$rate(double value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setDouble(columnInfo.rateIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setDouble(columnInfo.rateIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$review() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.reviewIndex);
    }

    @Override
    public void realmSet$review(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.reviewIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.reviewIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.reviewIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.reviewIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$pseudo() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.pseudoIndex);
    }

    @Override
    public void realmSet$pseudo(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.pseudoIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.pseudoIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.pseudoIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.pseudoIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$image() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.imageIndex);
    }

    @Override
    public void realmSet$image(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.imageIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.imageIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.imageIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.imageIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$guest_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.guest_idIndex);
    }

    @Override
    public void realmSet$guest_id(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.guest_idIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.guest_idIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("Review", 7, 0);
        builder.addPersistedProperty("id_rate", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("store_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("rate", RealmFieldType.DOUBLE, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("review", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("pseudo", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("image", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("guest_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static ReviewColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new ReviewColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "Review";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.directoriodelicias.apps.delicias.classes.Review createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.directoriodelicias.apps.delicias.classes.Review obj = null;
        if (update) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Review.class);
            ReviewColumnInfo columnInfo = (ReviewColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Review.class);
            long pkColumnIndex = columnInfo.id_rateIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("id_rate")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id_rate"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Review.class), false, Collections.<String> emptyList());
                    obj = new io.realm.ReviewRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("id_rate")) {
                if (json.isNull("id_rate")) {
                    obj = (io.realm.ReviewRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Review.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.ReviewRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Review.class, json.getInt("id_rate"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id_rate'.");
            }
        }

        final ReviewRealmProxyInterface objProxy = (ReviewRealmProxyInterface) obj;
        if (json.has("store_id")) {
            if (json.isNull("store_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'store_id' to null.");
            } else {
                objProxy.realmSet$store_id((int) json.getInt("store_id"));
            }
        }
        if (json.has("rate")) {
            if (json.isNull("rate")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'rate' to null.");
            } else {
                objProxy.realmSet$rate((double) json.getDouble("rate"));
            }
        }
        if (json.has("review")) {
            if (json.isNull("review")) {
                objProxy.realmSet$review(null);
            } else {
                objProxy.realmSet$review((String) json.getString("review"));
            }
        }
        if (json.has("pseudo")) {
            if (json.isNull("pseudo")) {
                objProxy.realmSet$pseudo(null);
            } else {
                objProxy.realmSet$pseudo((String) json.getString("pseudo"));
            }
        }
        if (json.has("image")) {
            if (json.isNull("image")) {
                objProxy.realmSet$image(null);
            } else {
                objProxy.realmSet$image((String) json.getString("image"));
            }
        }
        if (json.has("guest_id")) {
            if (json.isNull("guest_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'guest_id' to null.");
            } else {
                objProxy.realmSet$guest_id((int) json.getInt("guest_id"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.directoriodelicias.apps.delicias.classes.Review createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.directoriodelicias.apps.delicias.classes.Review obj = new com.directoriodelicias.apps.delicias.classes.Review();
        final ReviewRealmProxyInterface objProxy = (ReviewRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("id_rate")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$id_rate((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'id_rate' to null.");
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("store_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$store_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'store_id' to null.");
                }
            } else if (name.equals("rate")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$rate((double) reader.nextDouble());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'rate' to null.");
                }
            } else if (name.equals("review")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$review((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$review(null);
                }
            } else if (name.equals("pseudo")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$pseudo((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$pseudo(null);
                }
            } else if (name.equals("image")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$image((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$image(null);
                }
            } else if (name.equals("guest_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$guest_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'guest_id' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id_rate'.");
        }
        return realm.copyToRealm(obj);
    }

    public static com.directoriodelicias.apps.delicias.classes.Review copyOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Review object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.directoriodelicias.apps.delicias.classes.Review) cachedRealmObject;
        }

        com.directoriodelicias.apps.delicias.classes.Review realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Review.class);
            ReviewColumnInfo columnInfo = (ReviewColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Review.class);
            long pkColumnIndex = columnInfo.id_rateIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((ReviewRealmProxyInterface) object).realmGet$id_rate());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Review.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.ReviewRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.directoriodelicias.apps.delicias.classes.Review copy(Realm realm, com.directoriodelicias.apps.delicias.classes.Review newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.directoriodelicias.apps.delicias.classes.Review) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.directoriodelicias.apps.delicias.classes.Review realmObject = realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Review.class, ((ReviewRealmProxyInterface) newObject).realmGet$id_rate(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        ReviewRealmProxyInterface realmObjectSource = (ReviewRealmProxyInterface) newObject;
        ReviewRealmProxyInterface realmObjectCopy = (ReviewRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$store_id(realmObjectSource.realmGet$store_id());
        realmObjectCopy.realmSet$rate(realmObjectSource.realmGet$rate());
        realmObjectCopy.realmSet$review(realmObjectSource.realmGet$review());
        realmObjectCopy.realmSet$pseudo(realmObjectSource.realmGet$pseudo());
        realmObjectCopy.realmSet$image(realmObjectSource.realmGet$image());
        realmObjectCopy.realmSet$guest_id(realmObjectSource.realmGet$guest_id());
        return realmObject;
    }

    public static long insert(Realm realm, com.directoriodelicias.apps.delicias.classes.Review object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Review.class);
        long tableNativePtr = table.getNativePtr();
        ReviewColumnInfo columnInfo = (ReviewColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Review.class);
        long pkColumnIndex = columnInfo.id_rateIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((ReviewRealmProxyInterface) object).realmGet$id_rate();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((ReviewRealmProxyInterface) object).realmGet$id_rate());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((ReviewRealmProxyInterface) object).realmGet$id_rate());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        Table.nativeSetLong(tableNativePtr, columnInfo.store_idIndex, rowIndex, ((ReviewRealmProxyInterface) object).realmGet$store_id(), false);
        Table.nativeSetDouble(tableNativePtr, columnInfo.rateIndex, rowIndex, ((ReviewRealmProxyInterface) object).realmGet$rate(), false);
        String realmGet$review = ((ReviewRealmProxyInterface) object).realmGet$review();
        if (realmGet$review != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.reviewIndex, rowIndex, realmGet$review, false);
        }
        String realmGet$pseudo = ((ReviewRealmProxyInterface) object).realmGet$pseudo();
        if (realmGet$pseudo != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.pseudoIndex, rowIndex, realmGet$pseudo, false);
        }
        String realmGet$image = ((ReviewRealmProxyInterface) object).realmGet$image();
        if (realmGet$image != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageIndex, rowIndex, realmGet$image, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.guest_idIndex, rowIndex, ((ReviewRealmProxyInterface) object).realmGet$guest_id(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Review.class);
        long tableNativePtr = table.getNativePtr();
        ReviewColumnInfo columnInfo = (ReviewColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Review.class);
        long pkColumnIndex = columnInfo.id_rateIndex;
        com.directoriodelicias.apps.delicias.classes.Review object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Review) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((ReviewRealmProxyInterface) object).realmGet$id_rate();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((ReviewRealmProxyInterface) object).realmGet$id_rate());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((ReviewRealmProxyInterface) object).realmGet$id_rate());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            Table.nativeSetLong(tableNativePtr, columnInfo.store_idIndex, rowIndex, ((ReviewRealmProxyInterface) object).realmGet$store_id(), false);
            Table.nativeSetDouble(tableNativePtr, columnInfo.rateIndex, rowIndex, ((ReviewRealmProxyInterface) object).realmGet$rate(), false);
            String realmGet$review = ((ReviewRealmProxyInterface) object).realmGet$review();
            if (realmGet$review != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.reviewIndex, rowIndex, realmGet$review, false);
            }
            String realmGet$pseudo = ((ReviewRealmProxyInterface) object).realmGet$pseudo();
            if (realmGet$pseudo != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.pseudoIndex, rowIndex, realmGet$pseudo, false);
            }
            String realmGet$image = ((ReviewRealmProxyInterface) object).realmGet$image();
            if (realmGet$image != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.imageIndex, rowIndex, realmGet$image, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.guest_idIndex, rowIndex, ((ReviewRealmProxyInterface) object).realmGet$guest_id(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Review object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Review.class);
        long tableNativePtr = table.getNativePtr();
        ReviewColumnInfo columnInfo = (ReviewColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Review.class);
        long pkColumnIndex = columnInfo.id_rateIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((ReviewRealmProxyInterface) object).realmGet$id_rate();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((ReviewRealmProxyInterface) object).realmGet$id_rate());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((ReviewRealmProxyInterface) object).realmGet$id_rate());
        }
        cache.put(object, rowIndex);
        Table.nativeSetLong(tableNativePtr, columnInfo.store_idIndex, rowIndex, ((ReviewRealmProxyInterface) object).realmGet$store_id(), false);
        Table.nativeSetDouble(tableNativePtr, columnInfo.rateIndex, rowIndex, ((ReviewRealmProxyInterface) object).realmGet$rate(), false);
        String realmGet$review = ((ReviewRealmProxyInterface) object).realmGet$review();
        if (realmGet$review != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.reviewIndex, rowIndex, realmGet$review, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.reviewIndex, rowIndex, false);
        }
        String realmGet$pseudo = ((ReviewRealmProxyInterface) object).realmGet$pseudo();
        if (realmGet$pseudo != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.pseudoIndex, rowIndex, realmGet$pseudo, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.pseudoIndex, rowIndex, false);
        }
        String realmGet$image = ((ReviewRealmProxyInterface) object).realmGet$image();
        if (realmGet$image != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageIndex, rowIndex, realmGet$image, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.imageIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.guest_idIndex, rowIndex, ((ReviewRealmProxyInterface) object).realmGet$guest_id(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Review.class);
        long tableNativePtr = table.getNativePtr();
        ReviewColumnInfo columnInfo = (ReviewColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Review.class);
        long pkColumnIndex = columnInfo.id_rateIndex;
        com.directoriodelicias.apps.delicias.classes.Review object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Review) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((ReviewRealmProxyInterface) object).realmGet$id_rate();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((ReviewRealmProxyInterface) object).realmGet$id_rate());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((ReviewRealmProxyInterface) object).realmGet$id_rate());
            }
            cache.put(object, rowIndex);
            Table.nativeSetLong(tableNativePtr, columnInfo.store_idIndex, rowIndex, ((ReviewRealmProxyInterface) object).realmGet$store_id(), false);
            Table.nativeSetDouble(tableNativePtr, columnInfo.rateIndex, rowIndex, ((ReviewRealmProxyInterface) object).realmGet$rate(), false);
            String realmGet$review = ((ReviewRealmProxyInterface) object).realmGet$review();
            if (realmGet$review != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.reviewIndex, rowIndex, realmGet$review, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.reviewIndex, rowIndex, false);
            }
            String realmGet$pseudo = ((ReviewRealmProxyInterface) object).realmGet$pseudo();
            if (realmGet$pseudo != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.pseudoIndex, rowIndex, realmGet$pseudo, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.pseudoIndex, rowIndex, false);
            }
            String realmGet$image = ((ReviewRealmProxyInterface) object).realmGet$image();
            if (realmGet$image != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.imageIndex, rowIndex, realmGet$image, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.imageIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.guest_idIndex, rowIndex, ((ReviewRealmProxyInterface) object).realmGet$guest_id(), false);
        }
    }

    public static com.directoriodelicias.apps.delicias.classes.Review createDetachedCopy(com.directoriodelicias.apps.delicias.classes.Review realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.directoriodelicias.apps.delicias.classes.Review unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.directoriodelicias.apps.delicias.classes.Review();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.directoriodelicias.apps.delicias.classes.Review) cachedObject.object;
            }
            unmanagedObject = (com.directoriodelicias.apps.delicias.classes.Review) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        ReviewRealmProxyInterface unmanagedCopy = (ReviewRealmProxyInterface) unmanagedObject;
        ReviewRealmProxyInterface realmSource = (ReviewRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$id_rate(realmSource.realmGet$id_rate());
        unmanagedCopy.realmSet$store_id(realmSource.realmGet$store_id());
        unmanagedCopy.realmSet$rate(realmSource.realmGet$rate());
        unmanagedCopy.realmSet$review(realmSource.realmGet$review());
        unmanagedCopy.realmSet$pseudo(realmSource.realmGet$pseudo());
        unmanagedCopy.realmSet$image(realmSource.realmGet$image());
        unmanagedCopy.realmSet$guest_id(realmSource.realmGet$guest_id());

        return unmanagedObject;
    }

    static com.directoriodelicias.apps.delicias.classes.Review update(Realm realm, com.directoriodelicias.apps.delicias.classes.Review realmObject, com.directoriodelicias.apps.delicias.classes.Review newObject, Map<RealmModel, RealmObjectProxy> cache) {
        ReviewRealmProxyInterface realmObjectTarget = (ReviewRealmProxyInterface) realmObject;
        ReviewRealmProxyInterface realmObjectSource = (ReviewRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$store_id(realmObjectSource.realmGet$store_id());
        realmObjectTarget.realmSet$rate(realmObjectSource.realmGet$rate());
        realmObjectTarget.realmSet$review(realmObjectSource.realmGet$review());
        realmObjectTarget.realmSet$pseudo(realmObjectSource.realmGet$pseudo());
        realmObjectTarget.realmSet$image(realmObjectSource.realmGet$image());
        realmObjectTarget.realmSet$guest_id(realmObjectSource.realmGet$guest_id());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Review = proxy[");
        stringBuilder.append("{id_rate:");
        stringBuilder.append(realmGet$id_rate());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{store_id:");
        stringBuilder.append(realmGet$store_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{rate:");
        stringBuilder.append(realmGet$rate());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{review:");
        stringBuilder.append(realmGet$review() != null ? realmGet$review() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{pseudo:");
        stringBuilder.append(realmGet$pseudo() != null ? realmGet$pseudo() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{image:");
        stringBuilder.append(realmGet$image() != null ? realmGet$image() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{guest_id:");
        stringBuilder.append(realmGet$guest_id());
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
        ReviewRealmProxy aReview = (ReviewRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aReview.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aReview.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aReview.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
