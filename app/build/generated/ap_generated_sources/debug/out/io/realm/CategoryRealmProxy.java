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
public class CategoryRealmProxy extends com.directoriodelicias.apps.delicias.classes.Category
    implements RealmObjectProxy, CategoryRealmProxyInterface {

    static final class CategoryColumnInfo extends ColumnInfo {
        long numCatIndex;
        long typeIndex;
        long nameCatIndex;
        long parentCategoryIndex;
        long logoIndex;
        long nbr_storesIndex;
        long colorIndex;
        long _orderIndex;
        long iconIndex;
        long imagesIndex;
        long menuIndex;

        CategoryColumnInfo(OsSchemaInfo schemaInfo) {
            super(11);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("Category");
            this.numCatIndex = addColumnDetails("numCat", objectSchemaInfo);
            this.typeIndex = addColumnDetails("type", objectSchemaInfo);
            this.nameCatIndex = addColumnDetails("nameCat", objectSchemaInfo);
            this.parentCategoryIndex = addColumnDetails("parentCategory", objectSchemaInfo);
            this.logoIndex = addColumnDetails("logo", objectSchemaInfo);
            this.nbr_storesIndex = addColumnDetails("nbr_stores", objectSchemaInfo);
            this.colorIndex = addColumnDetails("color", objectSchemaInfo);
            this._orderIndex = addColumnDetails("_order", objectSchemaInfo);
            this.iconIndex = addColumnDetails("icon", objectSchemaInfo);
            this.imagesIndex = addColumnDetails("images", objectSchemaInfo);
            this.menuIndex = addColumnDetails("menu", objectSchemaInfo);
        }

        CategoryColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new CategoryColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final CategoryColumnInfo src = (CategoryColumnInfo) rawSrc;
            final CategoryColumnInfo dst = (CategoryColumnInfo) rawDst;
            dst.numCatIndex = src.numCatIndex;
            dst.typeIndex = src.typeIndex;
            dst.nameCatIndex = src.nameCatIndex;
            dst.parentCategoryIndex = src.parentCategoryIndex;
            dst.logoIndex = src.logoIndex;
            dst.nbr_storesIndex = src.nbr_storesIndex;
            dst.colorIndex = src.colorIndex;
            dst._orderIndex = src._orderIndex;
            dst.iconIndex = src.iconIndex;
            dst.imagesIndex = src.imagesIndex;
            dst.menuIndex = src.menuIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(11);
        fieldNames.add("numCat");
        fieldNames.add("type");
        fieldNames.add("nameCat");
        fieldNames.add("parentCategory");
        fieldNames.add("logo");
        fieldNames.add("nbr_stores");
        fieldNames.add("color");
        fieldNames.add("_order");
        fieldNames.add("icon");
        fieldNames.add("images");
        fieldNames.add("menu");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private CategoryColumnInfo columnInfo;
    private ProxyState<com.directoriodelicias.apps.delicias.classes.Category> proxyState;

    CategoryRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (CategoryColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.directoriodelicias.apps.delicias.classes.Category>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$numCat() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.numCatIndex);
    }

    @Override
    public void realmSet$numCat(int value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'numCat' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$type() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.typeIndex);
    }

    @Override
    public void realmSet$type(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.typeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.typeIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$nameCat() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.nameCatIndex);
    }

    @Override
    public void realmSet$nameCat(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.nameCatIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.nameCatIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.nameCatIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.nameCatIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$parentCategory() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.parentCategoryIndex);
    }

    @Override
    public void realmSet$parentCategory(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.parentCategoryIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.parentCategoryIndex, value);
    }

    @Override
    public com.directoriodelicias.apps.delicias.classes.Images realmGet$logo() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.logoIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(com.directoriodelicias.apps.delicias.classes.Images.class, proxyState.getRow$realm().getLink(columnInfo.logoIndex), false, Collections.<String>emptyList());
    }

    @Override
    public void realmSet$logo(com.directoriodelicias.apps.delicias.classes.Images value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("logo")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.logoIndex);
                return;
            }
            proxyState.checkValidObject(value);
            row.getTable().setLink(columnInfo.logoIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.logoIndex);
            return;
        }
        proxyState.checkValidObject(value);
        proxyState.getRow$realm().setLink(columnInfo.logoIndex, ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex());
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$nbr_stores() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.nbr_storesIndex);
    }

    @Override
    public void realmSet$nbr_stores(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.nbr_storesIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.nbr_storesIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$color() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.colorIndex);
    }

    @Override
    public void realmSet$color(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.colorIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.colorIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.colorIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.colorIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$_order() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo._orderIndex);
    }

    @Override
    public void realmSet$_order(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo._orderIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo._orderIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$icon() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.iconIndex);
    }

    @Override
    public void realmSet$icon(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.iconIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.iconIndex, value);
    }

    @Override
    public com.directoriodelicias.apps.delicias.classes.Images realmGet$images() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.imagesIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(com.directoriodelicias.apps.delicias.classes.Images.class, proxyState.getRow$realm().getLink(columnInfo.imagesIndex), false, Collections.<String>emptyList());
    }

    @Override
    public void realmSet$images(com.directoriodelicias.apps.delicias.classes.Images value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("images")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.imagesIndex);
                return;
            }
            proxyState.checkValidObject(value);
            row.getTable().setLink(columnInfo.imagesIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.imagesIndex);
            return;
        }
        proxyState.checkValidObject(value);
        proxyState.getRow$realm().setLink(columnInfo.imagesIndex, ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex());
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$menu() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.menuIndex);
    }

    @Override
    public void realmSet$menu(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.menuIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.menuIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("Category", 11, 0);
        builder.addPersistedProperty("numCat", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("type", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("nameCat", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("parentCategory", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedLinkProperty("logo", RealmFieldType.OBJECT, "Images");
        builder.addPersistedProperty("nbr_stores", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("color", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("_order", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("icon", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedLinkProperty("images", RealmFieldType.OBJECT, "Images");
        builder.addPersistedProperty("menu", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static CategoryColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new CategoryColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "Category";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.directoriodelicias.apps.delicias.classes.Category createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(2);
        com.directoriodelicias.apps.delicias.classes.Category obj = null;
        if (update) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Category.class);
            CategoryColumnInfo columnInfo = (CategoryColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Category.class);
            long pkColumnIndex = columnInfo.numCatIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("numCat")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("numCat"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Category.class), false, Collections.<String> emptyList());
                    obj = new io.realm.CategoryRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("logo")) {
                excludeFields.add("logo");
            }
            if (json.has("images")) {
                excludeFields.add("images");
            }
            if (json.has("numCat")) {
                if (json.isNull("numCat")) {
                    obj = (io.realm.CategoryRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Category.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.CategoryRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Category.class, json.getInt("numCat"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'numCat'.");
            }
        }

        final CategoryRealmProxyInterface objProxy = (CategoryRealmProxyInterface) obj;
        if (json.has("type")) {
            if (json.isNull("type")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'type' to null.");
            } else {
                objProxy.realmSet$type((int) json.getInt("type"));
            }
        }
        if (json.has("nameCat")) {
            if (json.isNull("nameCat")) {
                objProxy.realmSet$nameCat(null);
            } else {
                objProxy.realmSet$nameCat((String) json.getString("nameCat"));
            }
        }
        if (json.has("parentCategory")) {
            if (json.isNull("parentCategory")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'parentCategory' to null.");
            } else {
                objProxy.realmSet$parentCategory((int) json.getInt("parentCategory"));
            }
        }
        if (json.has("logo")) {
            if (json.isNull("logo")) {
                objProxy.realmSet$logo(null);
            } else {
                com.directoriodelicias.apps.delicias.classes.Images logoObj = ImagesRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("logo"), update);
                objProxy.realmSet$logo(logoObj);
            }
        }
        if (json.has("nbr_stores")) {
            if (json.isNull("nbr_stores")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'nbr_stores' to null.");
            } else {
                objProxy.realmSet$nbr_stores((int) json.getInt("nbr_stores"));
            }
        }
        if (json.has("color")) {
            if (json.isNull("color")) {
                objProxy.realmSet$color(null);
            } else {
                objProxy.realmSet$color((String) json.getString("color"));
            }
        }
        if (json.has("_order")) {
            if (json.isNull("_order")) {
                throw new IllegalArgumentException("Trying to set non-nullable field '_order' to null.");
            } else {
                objProxy.realmSet$_order((int) json.getInt("_order"));
            }
        }
        if (json.has("icon")) {
            if (json.isNull("icon")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'icon' to null.");
            } else {
                objProxy.realmSet$icon((int) json.getInt("icon"));
            }
        }
        if (json.has("images")) {
            if (json.isNull("images")) {
                objProxy.realmSet$images(null);
            } else {
                com.directoriodelicias.apps.delicias.classes.Images imagesObj = ImagesRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("images"), update);
                objProxy.realmSet$images(imagesObj);
            }
        }
        if (json.has("menu")) {
            if (json.isNull("menu")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'menu' to null.");
            } else {
                objProxy.realmSet$menu((boolean) json.getBoolean("menu"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.directoriodelicias.apps.delicias.classes.Category createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.directoriodelicias.apps.delicias.classes.Category obj = new com.directoriodelicias.apps.delicias.classes.Category();
        final CategoryRealmProxyInterface objProxy = (CategoryRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("numCat")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$numCat((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'numCat' to null.");
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("type")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$type((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'type' to null.");
                }
            } else if (name.equals("nameCat")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$nameCat((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$nameCat(null);
                }
            } else if (name.equals("parentCategory")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$parentCategory((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'parentCategory' to null.");
                }
            } else if (name.equals("logo")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$logo(null);
                } else {
                    com.directoriodelicias.apps.delicias.classes.Images logoObj = ImagesRealmProxy.createUsingJsonStream(realm, reader);
                    objProxy.realmSet$logo(logoObj);
                }
            } else if (name.equals("nbr_stores")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$nbr_stores((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'nbr_stores' to null.");
                }
            } else if (name.equals("color")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$color((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$color(null);
                }
            } else if (name.equals("_order")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$_order((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field '_order' to null.");
                }
            } else if (name.equals("icon")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$icon((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'icon' to null.");
                }
            } else if (name.equals("images")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$images(null);
                } else {
                    com.directoriodelicias.apps.delicias.classes.Images imagesObj = ImagesRealmProxy.createUsingJsonStream(realm, reader);
                    objProxy.realmSet$images(imagesObj);
                }
            } else if (name.equals("menu")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$menu((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'menu' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'numCat'.");
        }
        return realm.copyToRealm(obj);
    }

    public static com.directoriodelicias.apps.delicias.classes.Category copyOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Category object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.directoriodelicias.apps.delicias.classes.Category) cachedRealmObject;
        }

        com.directoriodelicias.apps.delicias.classes.Category realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Category.class);
            CategoryColumnInfo columnInfo = (CategoryColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Category.class);
            long pkColumnIndex = columnInfo.numCatIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((CategoryRealmProxyInterface) object).realmGet$numCat());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Category.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.CategoryRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.directoriodelicias.apps.delicias.classes.Category copy(Realm realm, com.directoriodelicias.apps.delicias.classes.Category newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.directoriodelicias.apps.delicias.classes.Category) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.directoriodelicias.apps.delicias.classes.Category realmObject = realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Category.class, ((CategoryRealmProxyInterface) newObject).realmGet$numCat(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        CategoryRealmProxyInterface realmObjectSource = (CategoryRealmProxyInterface) newObject;
        CategoryRealmProxyInterface realmObjectCopy = (CategoryRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$type(realmObjectSource.realmGet$type());
        realmObjectCopy.realmSet$nameCat(realmObjectSource.realmGet$nameCat());
        realmObjectCopy.realmSet$parentCategory(realmObjectSource.realmGet$parentCategory());

        com.directoriodelicias.apps.delicias.classes.Images logoObj = realmObjectSource.realmGet$logo();
        if (logoObj == null) {
            realmObjectCopy.realmSet$logo(null);
        } else {
            com.directoriodelicias.apps.delicias.classes.Images cachelogo = (com.directoriodelicias.apps.delicias.classes.Images) cache.get(logoObj);
            if (cachelogo != null) {
                realmObjectCopy.realmSet$logo(cachelogo);
            } else {
                realmObjectCopy.realmSet$logo(ImagesRealmProxy.copyOrUpdate(realm, logoObj, update, cache));
            }
        }
        realmObjectCopy.realmSet$nbr_stores(realmObjectSource.realmGet$nbr_stores());
        realmObjectCopy.realmSet$color(realmObjectSource.realmGet$color());
        realmObjectCopy.realmSet$_order(realmObjectSource.realmGet$_order());
        realmObjectCopy.realmSet$icon(realmObjectSource.realmGet$icon());

        com.directoriodelicias.apps.delicias.classes.Images imagesObj = realmObjectSource.realmGet$images();
        if (imagesObj == null) {
            realmObjectCopy.realmSet$images(null);
        } else {
            com.directoriodelicias.apps.delicias.classes.Images cacheimages = (com.directoriodelicias.apps.delicias.classes.Images) cache.get(imagesObj);
            if (cacheimages != null) {
                realmObjectCopy.realmSet$images(cacheimages);
            } else {
                realmObjectCopy.realmSet$images(ImagesRealmProxy.copyOrUpdate(realm, imagesObj, update, cache));
            }
        }
        realmObjectCopy.realmSet$menu(realmObjectSource.realmGet$menu());
        return realmObject;
    }

    public static long insert(Realm realm, com.directoriodelicias.apps.delicias.classes.Category object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Category.class);
        long tableNativePtr = table.getNativePtr();
        CategoryColumnInfo columnInfo = (CategoryColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Category.class);
        long pkColumnIndex = columnInfo.numCatIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((CategoryRealmProxyInterface) object).realmGet$numCat();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((CategoryRealmProxyInterface) object).realmGet$numCat());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((CategoryRealmProxyInterface) object).realmGet$numCat());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        Table.nativeSetLong(tableNativePtr, columnInfo.typeIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$type(), false);
        String realmGet$nameCat = ((CategoryRealmProxyInterface) object).realmGet$nameCat();
        if (realmGet$nameCat != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameCatIndex, rowIndex, realmGet$nameCat, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.parentCategoryIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$parentCategory(), false);

        com.directoriodelicias.apps.delicias.classes.Images logoObj = ((CategoryRealmProxyInterface) object).realmGet$logo();
        if (logoObj != null) {
            Long cachelogo = cache.get(logoObj);
            if (cachelogo == null) {
                cachelogo = ImagesRealmProxy.insert(realm, logoObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.logoIndex, rowIndex, cachelogo, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.nbr_storesIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$nbr_stores(), false);
        String realmGet$color = ((CategoryRealmProxyInterface) object).realmGet$color();
        if (realmGet$color != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.colorIndex, rowIndex, realmGet$color, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo._orderIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$_order(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.iconIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$icon(), false);

        com.directoriodelicias.apps.delicias.classes.Images imagesObj = ((CategoryRealmProxyInterface) object).realmGet$images();
        if (imagesObj != null) {
            Long cacheimages = cache.get(imagesObj);
            if (cacheimages == null) {
                cacheimages = ImagesRealmProxy.insert(realm, imagesObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.imagesIndex, rowIndex, cacheimages, false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.menuIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$menu(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Category.class);
        long tableNativePtr = table.getNativePtr();
        CategoryColumnInfo columnInfo = (CategoryColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Category.class);
        long pkColumnIndex = columnInfo.numCatIndex;
        com.directoriodelicias.apps.delicias.classes.Category object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Category) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((CategoryRealmProxyInterface) object).realmGet$numCat();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((CategoryRealmProxyInterface) object).realmGet$numCat());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((CategoryRealmProxyInterface) object).realmGet$numCat());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            Table.nativeSetLong(tableNativePtr, columnInfo.typeIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$type(), false);
            String realmGet$nameCat = ((CategoryRealmProxyInterface) object).realmGet$nameCat();
            if (realmGet$nameCat != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.nameCatIndex, rowIndex, realmGet$nameCat, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.parentCategoryIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$parentCategory(), false);

            com.directoriodelicias.apps.delicias.classes.Images logoObj = ((CategoryRealmProxyInterface) object).realmGet$logo();
            if (logoObj != null) {
                Long cachelogo = cache.get(logoObj);
                if (cachelogo == null) {
                    cachelogo = ImagesRealmProxy.insert(realm, logoObj, cache);
                }
                table.setLink(columnInfo.logoIndex, rowIndex, cachelogo, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.nbr_storesIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$nbr_stores(), false);
            String realmGet$color = ((CategoryRealmProxyInterface) object).realmGet$color();
            if (realmGet$color != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.colorIndex, rowIndex, realmGet$color, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo._orderIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$_order(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.iconIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$icon(), false);

            com.directoriodelicias.apps.delicias.classes.Images imagesObj = ((CategoryRealmProxyInterface) object).realmGet$images();
            if (imagesObj != null) {
                Long cacheimages = cache.get(imagesObj);
                if (cacheimages == null) {
                    cacheimages = ImagesRealmProxy.insert(realm, imagesObj, cache);
                }
                table.setLink(columnInfo.imagesIndex, rowIndex, cacheimages, false);
            }
            Table.nativeSetBoolean(tableNativePtr, columnInfo.menuIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$menu(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Category object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Category.class);
        long tableNativePtr = table.getNativePtr();
        CategoryColumnInfo columnInfo = (CategoryColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Category.class);
        long pkColumnIndex = columnInfo.numCatIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((CategoryRealmProxyInterface) object).realmGet$numCat();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((CategoryRealmProxyInterface) object).realmGet$numCat());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((CategoryRealmProxyInterface) object).realmGet$numCat());
        }
        cache.put(object, rowIndex);
        Table.nativeSetLong(tableNativePtr, columnInfo.typeIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$type(), false);
        String realmGet$nameCat = ((CategoryRealmProxyInterface) object).realmGet$nameCat();
        if (realmGet$nameCat != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameCatIndex, rowIndex, realmGet$nameCat, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.nameCatIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.parentCategoryIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$parentCategory(), false);

        com.directoriodelicias.apps.delicias.classes.Images logoObj = ((CategoryRealmProxyInterface) object).realmGet$logo();
        if (logoObj != null) {
            Long cachelogo = cache.get(logoObj);
            if (cachelogo == null) {
                cachelogo = ImagesRealmProxy.insertOrUpdate(realm, logoObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.logoIndex, rowIndex, cachelogo, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.logoIndex, rowIndex);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.nbr_storesIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$nbr_stores(), false);
        String realmGet$color = ((CategoryRealmProxyInterface) object).realmGet$color();
        if (realmGet$color != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.colorIndex, rowIndex, realmGet$color, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.colorIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo._orderIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$_order(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.iconIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$icon(), false);

        com.directoriodelicias.apps.delicias.classes.Images imagesObj = ((CategoryRealmProxyInterface) object).realmGet$images();
        if (imagesObj != null) {
            Long cacheimages = cache.get(imagesObj);
            if (cacheimages == null) {
                cacheimages = ImagesRealmProxy.insertOrUpdate(realm, imagesObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.imagesIndex, rowIndex, cacheimages, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.imagesIndex, rowIndex);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.menuIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$menu(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Category.class);
        long tableNativePtr = table.getNativePtr();
        CategoryColumnInfo columnInfo = (CategoryColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Category.class);
        long pkColumnIndex = columnInfo.numCatIndex;
        com.directoriodelicias.apps.delicias.classes.Category object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Category) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((CategoryRealmProxyInterface) object).realmGet$numCat();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((CategoryRealmProxyInterface) object).realmGet$numCat());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((CategoryRealmProxyInterface) object).realmGet$numCat());
            }
            cache.put(object, rowIndex);
            Table.nativeSetLong(tableNativePtr, columnInfo.typeIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$type(), false);
            String realmGet$nameCat = ((CategoryRealmProxyInterface) object).realmGet$nameCat();
            if (realmGet$nameCat != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.nameCatIndex, rowIndex, realmGet$nameCat, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.nameCatIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.parentCategoryIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$parentCategory(), false);

            com.directoriodelicias.apps.delicias.classes.Images logoObj = ((CategoryRealmProxyInterface) object).realmGet$logo();
            if (logoObj != null) {
                Long cachelogo = cache.get(logoObj);
                if (cachelogo == null) {
                    cachelogo = ImagesRealmProxy.insertOrUpdate(realm, logoObj, cache);
                }
                Table.nativeSetLink(tableNativePtr, columnInfo.logoIndex, rowIndex, cachelogo, false);
            } else {
                Table.nativeNullifyLink(tableNativePtr, columnInfo.logoIndex, rowIndex);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.nbr_storesIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$nbr_stores(), false);
            String realmGet$color = ((CategoryRealmProxyInterface) object).realmGet$color();
            if (realmGet$color != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.colorIndex, rowIndex, realmGet$color, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.colorIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo._orderIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$_order(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.iconIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$icon(), false);

            com.directoriodelicias.apps.delicias.classes.Images imagesObj = ((CategoryRealmProxyInterface) object).realmGet$images();
            if (imagesObj != null) {
                Long cacheimages = cache.get(imagesObj);
                if (cacheimages == null) {
                    cacheimages = ImagesRealmProxy.insertOrUpdate(realm, imagesObj, cache);
                }
                Table.nativeSetLink(tableNativePtr, columnInfo.imagesIndex, rowIndex, cacheimages, false);
            } else {
                Table.nativeNullifyLink(tableNativePtr, columnInfo.imagesIndex, rowIndex);
            }
            Table.nativeSetBoolean(tableNativePtr, columnInfo.menuIndex, rowIndex, ((CategoryRealmProxyInterface) object).realmGet$menu(), false);
        }
    }

    public static com.directoriodelicias.apps.delicias.classes.Category createDetachedCopy(com.directoriodelicias.apps.delicias.classes.Category realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.directoriodelicias.apps.delicias.classes.Category unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.directoriodelicias.apps.delicias.classes.Category();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.directoriodelicias.apps.delicias.classes.Category) cachedObject.object;
            }
            unmanagedObject = (com.directoriodelicias.apps.delicias.classes.Category) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        CategoryRealmProxyInterface unmanagedCopy = (CategoryRealmProxyInterface) unmanagedObject;
        CategoryRealmProxyInterface realmSource = (CategoryRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$numCat(realmSource.realmGet$numCat());
        unmanagedCopy.realmSet$type(realmSource.realmGet$type());
        unmanagedCopy.realmSet$nameCat(realmSource.realmGet$nameCat());
        unmanagedCopy.realmSet$parentCategory(realmSource.realmGet$parentCategory());

        // Deep copy of logo
        unmanagedCopy.realmSet$logo(ImagesRealmProxy.createDetachedCopy(realmSource.realmGet$logo(), currentDepth + 1, maxDepth, cache));
        unmanagedCopy.realmSet$nbr_stores(realmSource.realmGet$nbr_stores());
        unmanagedCopy.realmSet$color(realmSource.realmGet$color());
        unmanagedCopy.realmSet$_order(realmSource.realmGet$_order());
        unmanagedCopy.realmSet$icon(realmSource.realmGet$icon());

        // Deep copy of images
        unmanagedCopy.realmSet$images(ImagesRealmProxy.createDetachedCopy(realmSource.realmGet$images(), currentDepth + 1, maxDepth, cache));
        unmanagedCopy.realmSet$menu(realmSource.realmGet$menu());

        return unmanagedObject;
    }

    static com.directoriodelicias.apps.delicias.classes.Category update(Realm realm, com.directoriodelicias.apps.delicias.classes.Category realmObject, com.directoriodelicias.apps.delicias.classes.Category newObject, Map<RealmModel, RealmObjectProxy> cache) {
        CategoryRealmProxyInterface realmObjectTarget = (CategoryRealmProxyInterface) realmObject;
        CategoryRealmProxyInterface realmObjectSource = (CategoryRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$type(realmObjectSource.realmGet$type());
        realmObjectTarget.realmSet$nameCat(realmObjectSource.realmGet$nameCat());
        realmObjectTarget.realmSet$parentCategory(realmObjectSource.realmGet$parentCategory());
        com.directoriodelicias.apps.delicias.classes.Images logoObj = realmObjectSource.realmGet$logo();
        if (logoObj == null) {
            realmObjectTarget.realmSet$logo(null);
        } else {
            com.directoriodelicias.apps.delicias.classes.Images cachelogo = (com.directoriodelicias.apps.delicias.classes.Images) cache.get(logoObj);
            if (cachelogo != null) {
                realmObjectTarget.realmSet$logo(cachelogo);
            } else {
                realmObjectTarget.realmSet$logo(ImagesRealmProxy.copyOrUpdate(realm, logoObj, true, cache));
            }
        }
        realmObjectTarget.realmSet$nbr_stores(realmObjectSource.realmGet$nbr_stores());
        realmObjectTarget.realmSet$color(realmObjectSource.realmGet$color());
        realmObjectTarget.realmSet$_order(realmObjectSource.realmGet$_order());
        realmObjectTarget.realmSet$icon(realmObjectSource.realmGet$icon());
        com.directoriodelicias.apps.delicias.classes.Images imagesObj = realmObjectSource.realmGet$images();
        if (imagesObj == null) {
            realmObjectTarget.realmSet$images(null);
        } else {
            com.directoriodelicias.apps.delicias.classes.Images cacheimages = (com.directoriodelicias.apps.delicias.classes.Images) cache.get(imagesObj);
            if (cacheimages != null) {
                realmObjectTarget.realmSet$images(cacheimages);
            } else {
                realmObjectTarget.realmSet$images(ImagesRealmProxy.copyOrUpdate(realm, imagesObj, true, cache));
            }
        }
        realmObjectTarget.realmSet$menu(realmObjectSource.realmGet$menu());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Category = proxy[");
        stringBuilder.append("{numCat:");
        stringBuilder.append(realmGet$numCat());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{type:");
        stringBuilder.append(realmGet$type());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{nameCat:");
        stringBuilder.append(realmGet$nameCat() != null ? realmGet$nameCat() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{parentCategory:");
        stringBuilder.append(realmGet$parentCategory());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{logo:");
        stringBuilder.append(realmGet$logo() != null ? "Images" : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{nbr_stores:");
        stringBuilder.append(realmGet$nbr_stores());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{color:");
        stringBuilder.append(realmGet$color() != null ? realmGet$color() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{_order:");
        stringBuilder.append(realmGet$_order());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{icon:");
        stringBuilder.append(realmGet$icon());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{images:");
        stringBuilder.append(realmGet$images() != null ? "Images" : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{menu:");
        stringBuilder.append(realmGet$menu());
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
        CategoryRealmProxy aCategory = (CategoryRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aCategory.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aCategory.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aCategory.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
