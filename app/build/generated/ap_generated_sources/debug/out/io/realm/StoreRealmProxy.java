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
public class StoreRealmProxy extends com.directoriodelicias.apps.delicias.classes.Store
    implements RealmObjectProxy, StoreRealmProxyInterface {

    static final class StoreColumnInfo extends ColumnInfo {
        long idIndex;
        long nameIndex;
        long addressIndex;
        long imagesIndex;
        long latitudeIndex;
        long longitudeIndex;
        long distanceIndex;
        long descriptionIndex;
        long typeIndex;
        long statusIndex;
        long detailIndex;
        long userIndex;
        long user_idIndex;
        long imageJsonIndex;
        long VotedIndex;
        long votesIndex;
        long nbr_votesIndex;
        long listImagesIndex;
        long phoneIndex;
        long savedIndex;
        long nbrOffersIndex;
        long lastOfferIndex;
        long category_idIndex;
        long featuredIndex;
        long galleryIndex;
        long category_nameIndex;
        long category_colorIndex;
        long canChatIndex;
        long openingIndex;
        long opening_time_tableIndex;
        long opening_time_table_listIndex;
        long linkIndex;

        StoreColumnInfo(OsSchemaInfo schemaInfo) {
            super(32);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("Store");
            this.idIndex = addColumnDetails("id", objectSchemaInfo);
            this.nameIndex = addColumnDetails("name", objectSchemaInfo);
            this.addressIndex = addColumnDetails("address", objectSchemaInfo);
            this.imagesIndex = addColumnDetails("images", objectSchemaInfo);
            this.latitudeIndex = addColumnDetails("latitude", objectSchemaInfo);
            this.longitudeIndex = addColumnDetails("longitude", objectSchemaInfo);
            this.distanceIndex = addColumnDetails("distance", objectSchemaInfo);
            this.descriptionIndex = addColumnDetails("description", objectSchemaInfo);
            this.typeIndex = addColumnDetails("type", objectSchemaInfo);
            this.statusIndex = addColumnDetails("status", objectSchemaInfo);
            this.detailIndex = addColumnDetails("detail", objectSchemaInfo);
            this.userIndex = addColumnDetails("user", objectSchemaInfo);
            this.user_idIndex = addColumnDetails("user_id", objectSchemaInfo);
            this.imageJsonIndex = addColumnDetails("imageJson", objectSchemaInfo);
            this.VotedIndex = addColumnDetails("Voted", objectSchemaInfo);
            this.votesIndex = addColumnDetails("votes", objectSchemaInfo);
            this.nbr_votesIndex = addColumnDetails("nbr_votes", objectSchemaInfo);
            this.listImagesIndex = addColumnDetails("listImages", objectSchemaInfo);
            this.phoneIndex = addColumnDetails("phone", objectSchemaInfo);
            this.savedIndex = addColumnDetails("saved", objectSchemaInfo);
            this.nbrOffersIndex = addColumnDetails("nbrOffers", objectSchemaInfo);
            this.lastOfferIndex = addColumnDetails("lastOffer", objectSchemaInfo);
            this.category_idIndex = addColumnDetails("category_id", objectSchemaInfo);
            this.featuredIndex = addColumnDetails("featured", objectSchemaInfo);
            this.galleryIndex = addColumnDetails("gallery", objectSchemaInfo);
            this.category_nameIndex = addColumnDetails("category_name", objectSchemaInfo);
            this.category_colorIndex = addColumnDetails("category_color", objectSchemaInfo);
            this.canChatIndex = addColumnDetails("canChat", objectSchemaInfo);
            this.openingIndex = addColumnDetails("opening", objectSchemaInfo);
            this.opening_time_tableIndex = addColumnDetails("opening_time_table", objectSchemaInfo);
            this.opening_time_table_listIndex = addColumnDetails("opening_time_table_list", objectSchemaInfo);
            this.linkIndex = addColumnDetails("link", objectSchemaInfo);
        }

        StoreColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new StoreColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final StoreColumnInfo src = (StoreColumnInfo) rawSrc;
            final StoreColumnInfo dst = (StoreColumnInfo) rawDst;
            dst.idIndex = src.idIndex;
            dst.nameIndex = src.nameIndex;
            dst.addressIndex = src.addressIndex;
            dst.imagesIndex = src.imagesIndex;
            dst.latitudeIndex = src.latitudeIndex;
            dst.longitudeIndex = src.longitudeIndex;
            dst.distanceIndex = src.distanceIndex;
            dst.descriptionIndex = src.descriptionIndex;
            dst.typeIndex = src.typeIndex;
            dst.statusIndex = src.statusIndex;
            dst.detailIndex = src.detailIndex;
            dst.userIndex = src.userIndex;
            dst.user_idIndex = src.user_idIndex;
            dst.imageJsonIndex = src.imageJsonIndex;
            dst.VotedIndex = src.VotedIndex;
            dst.votesIndex = src.votesIndex;
            dst.nbr_votesIndex = src.nbr_votesIndex;
            dst.listImagesIndex = src.listImagesIndex;
            dst.phoneIndex = src.phoneIndex;
            dst.savedIndex = src.savedIndex;
            dst.nbrOffersIndex = src.nbrOffersIndex;
            dst.lastOfferIndex = src.lastOfferIndex;
            dst.category_idIndex = src.category_idIndex;
            dst.featuredIndex = src.featuredIndex;
            dst.galleryIndex = src.galleryIndex;
            dst.category_nameIndex = src.category_nameIndex;
            dst.category_colorIndex = src.category_colorIndex;
            dst.canChatIndex = src.canChatIndex;
            dst.openingIndex = src.openingIndex;
            dst.opening_time_tableIndex = src.opening_time_tableIndex;
            dst.opening_time_table_listIndex = src.opening_time_table_listIndex;
            dst.linkIndex = src.linkIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(32);
        fieldNames.add("id");
        fieldNames.add("name");
        fieldNames.add("address");
        fieldNames.add("images");
        fieldNames.add("latitude");
        fieldNames.add("longitude");
        fieldNames.add("distance");
        fieldNames.add("description");
        fieldNames.add("type");
        fieldNames.add("status");
        fieldNames.add("detail");
        fieldNames.add("user");
        fieldNames.add("user_id");
        fieldNames.add("imageJson");
        fieldNames.add("Voted");
        fieldNames.add("votes");
        fieldNames.add("nbr_votes");
        fieldNames.add("listImages");
        fieldNames.add("phone");
        fieldNames.add("saved");
        fieldNames.add("nbrOffers");
        fieldNames.add("lastOffer");
        fieldNames.add("category_id");
        fieldNames.add("featured");
        fieldNames.add("gallery");
        fieldNames.add("category_name");
        fieldNames.add("category_color");
        fieldNames.add("canChat");
        fieldNames.add("opening");
        fieldNames.add("opening_time_table");
        fieldNames.add("opening_time_table_list");
        fieldNames.add("link");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private StoreColumnInfo columnInfo;
    private ProxyState<com.directoriodelicias.apps.delicias.classes.Store> proxyState;
    private RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesRealmList;
    private RealmList<com.directoriodelicias.apps.delicias.classes.OpeningTime> opening_time_table_listRealmList;

    StoreRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (StoreColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.directoriodelicias.apps.delicias.classes.Store>(this);
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
    public String realmGet$name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.nameIndex);
    }

    @Override
    public void realmSet$name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.nameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.nameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.nameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.nameIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$address() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.addressIndex);
    }

    @Override
    public void realmSet$address(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.addressIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.addressIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.addressIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.addressIndex, value);
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
    public Double realmGet$latitude() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.latitudeIndex)) {
            return null;
        }
        return (double) proxyState.getRow$realm().getDouble(columnInfo.latitudeIndex);
    }

    @Override
    public void realmSet$latitude(Double value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.latitudeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setDouble(columnInfo.latitudeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.latitudeIndex);
            return;
        }
        proxyState.getRow$realm().setDouble(columnInfo.latitudeIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Double realmGet$longitude() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.longitudeIndex)) {
            return null;
        }
        return (double) proxyState.getRow$realm().getDouble(columnInfo.longitudeIndex);
    }

    @Override
    public void realmSet$longitude(Double value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.longitudeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setDouble(columnInfo.longitudeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.longitudeIndex);
            return;
        }
        proxyState.getRow$realm().setDouble(columnInfo.longitudeIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Double realmGet$distance() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.distanceIndex)) {
            return null;
        }
        return (double) proxyState.getRow$realm().getDouble(columnInfo.distanceIndex);
    }

    @Override
    public void realmSet$distance(Double value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.distanceIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setDouble(columnInfo.distanceIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.distanceIndex);
            return;
        }
        proxyState.getRow$realm().setDouble(columnInfo.distanceIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$description() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.descriptionIndex);
    }

    @Override
    public void realmSet$description(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.descriptionIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.descriptionIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.descriptionIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.descriptionIndex, value);
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
    public int realmGet$status() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.statusIndex);
    }

    @Override
    public void realmSet$status(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.statusIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.statusIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$detail() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.detailIndex);
    }

    @Override
    public void realmSet$detail(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.detailIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.detailIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.detailIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.detailIndex, value);
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

    @Override
    @SuppressWarnings("cast")
    public int realmGet$user_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.user_idIndex);
    }

    @Override
    public void realmSet$user_id(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.user_idIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.user_idIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$imageJson() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.imageJsonIndex);
    }

    @Override
    public void realmSet$imageJson(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.imageJsonIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.imageJsonIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.imageJsonIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.imageJsonIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$Voted() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.VotedIndex);
    }

    @Override
    public void realmSet$Voted(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.VotedIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.VotedIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public double realmGet$votes() {
        proxyState.getRealm$realm().checkIfValid();
        return (double) proxyState.getRow$realm().getDouble(columnInfo.votesIndex);
    }

    @Override
    public void realmSet$votes(double value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setDouble(columnInfo.votesIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setDouble(columnInfo.votesIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$nbr_votes() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.nbr_votesIndex);
    }

    @Override
    public void realmSet$nbr_votes(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.nbr_votesIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.nbr_votesIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.nbr_votesIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.nbr_votesIndex, value);
    }

    @Override
    public RealmList<com.directoriodelicias.apps.delicias.classes.Images> realmGet$listImages() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (listImagesRealmList != null) {
            return listImagesRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.listImagesIndex);
            listImagesRealmList = new RealmList<com.directoriodelicias.apps.delicias.classes.Images>(com.directoriodelicias.apps.delicias.classes.Images.class, osList, proxyState.getRealm$realm());
            return listImagesRealmList;
        }
    }

    @Override
    public void realmSet$listImages(RealmList<com.directoriodelicias.apps.delicias.classes.Images> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("listImages")) {
                return;
            }
            // if the list contains unmanaged RealmObjects, convert them to managed.
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.directoriodelicias.apps.delicias.classes.Images> original = value;
                value = new RealmList<com.directoriodelicias.apps.delicias.classes.Images>();
                for (com.directoriodelicias.apps.delicias.classes.Images item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.listImagesIndex);
        // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
        if (value != null && value.size() == osList.size()) {
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.directoriodelicias.apps.delicias.classes.Images linkedObject = value.get(i);
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
                com.directoriodelicias.apps.delicias.classes.Images linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.addRow(((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        }
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$phone() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.phoneIndex);
    }

    @Override
    public void realmSet$phone(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.phoneIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.phoneIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.phoneIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.phoneIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$saved() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.savedIndex);
    }

    @Override
    public void realmSet$saved(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.savedIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.savedIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$nbrOffers() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.nbrOffersIndex);
    }

    @Override
    public void realmSet$nbrOffers(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.nbrOffersIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.nbrOffersIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$lastOffer() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.lastOfferIndex);
    }

    @Override
    public void realmSet$lastOffer(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.lastOfferIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.lastOfferIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.lastOfferIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.lastOfferIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$category_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.category_idIndex);
    }

    @Override
    public void realmSet$category_id(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.category_idIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.category_idIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$featured() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.featuredIndex);
    }

    @Override
    public void realmSet$featured(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.featuredIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.featuredIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$gallery() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.galleryIndex);
    }

    @Override
    public void realmSet$gallery(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.galleryIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.galleryIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$category_name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.category_nameIndex);
    }

    @Override
    public void realmSet$category_name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.category_nameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.category_nameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.category_nameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.category_nameIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$category_color() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.category_colorIndex);
    }

    @Override
    public void realmSet$category_color(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.category_colorIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.category_colorIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.category_colorIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.category_colorIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$canChat() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.canChatIndex);
    }

    @Override
    public void realmSet$canChat(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.canChatIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.canChatIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$opening() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.openingIndex);
    }

    @Override
    public void realmSet$opening(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.openingIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.openingIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$opening_time_table() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.opening_time_tableIndex);
    }

    @Override
    public void realmSet$opening_time_table(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.opening_time_tableIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.opening_time_tableIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.opening_time_tableIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.opening_time_tableIndex, value);
    }

    @Override
    public RealmList<com.directoriodelicias.apps.delicias.classes.OpeningTime> realmGet$opening_time_table_list() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (opening_time_table_listRealmList != null) {
            return opening_time_table_listRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.opening_time_table_listIndex);
            opening_time_table_listRealmList = new RealmList<com.directoriodelicias.apps.delicias.classes.OpeningTime>(com.directoriodelicias.apps.delicias.classes.OpeningTime.class, osList, proxyState.getRealm$realm());
            return opening_time_table_listRealmList;
        }
    }

    @Override
    public void realmSet$opening_time_table_list(RealmList<com.directoriodelicias.apps.delicias.classes.OpeningTime> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("opening_time_table_list")) {
                return;
            }
            // if the list contains unmanaged RealmObjects, convert them to managed.
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.directoriodelicias.apps.delicias.classes.OpeningTime> original = value;
                value = new RealmList<com.directoriodelicias.apps.delicias.classes.OpeningTime>();
                for (com.directoriodelicias.apps.delicias.classes.OpeningTime item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.opening_time_table_listIndex);
        // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
        if (value != null && value.size() == osList.size()) {
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.directoriodelicias.apps.delicias.classes.OpeningTime linkedObject = value.get(i);
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
                com.directoriodelicias.apps.delicias.classes.OpeningTime linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.addRow(((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        }
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$link() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.linkIndex);
    }

    @Override
    public void realmSet$link(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.linkIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.linkIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.linkIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.linkIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("Store", 32, 0);
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("address", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedLinkProperty("images", RealmFieldType.OBJECT, "Images");
        builder.addPersistedProperty("latitude", RealmFieldType.DOUBLE, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("longitude", RealmFieldType.DOUBLE, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("distance", RealmFieldType.DOUBLE, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("description", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("type", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("status", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("detail", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedLinkProperty("user", RealmFieldType.OBJECT, "User");
        builder.addPersistedProperty("user_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("imageJson", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("Voted", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("votes", RealmFieldType.DOUBLE, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("nbr_votes", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedLinkProperty("listImages", RealmFieldType.LIST, "Images");
        builder.addPersistedProperty("phone", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("saved", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("nbrOffers", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("lastOffer", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("category_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("featured", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("gallery", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("category_name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("category_color", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("canChat", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("opening", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("opening_time_table", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedLinkProperty("opening_time_table_list", RealmFieldType.LIST, "OpeningTime");
        builder.addPersistedProperty("link", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static StoreColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new StoreColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "Store";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.directoriodelicias.apps.delicias.classes.Store createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(4);
        com.directoriodelicias.apps.delicias.classes.Store obj = null;
        if (update) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Store.class);
            StoreColumnInfo columnInfo = (StoreColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Store.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Store.class), false, Collections.<String> emptyList());
                    obj = new io.realm.StoreRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("images")) {
                excludeFields.add("images");
            }
            if (json.has("user")) {
                excludeFields.add("user");
            }
            if (json.has("listImages")) {
                excludeFields.add("listImages");
            }
            if (json.has("opening_time_table_list")) {
                excludeFields.add("opening_time_table_list");
            }
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.StoreRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Store.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.StoreRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Store.class, json.getInt("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }

        final StoreRealmProxyInterface objProxy = (StoreRealmProxyInterface) obj;
        if (json.has("name")) {
            if (json.isNull("name")) {
                objProxy.realmSet$name(null);
            } else {
                objProxy.realmSet$name((String) json.getString("name"));
            }
        }
        if (json.has("address")) {
            if (json.isNull("address")) {
                objProxy.realmSet$address(null);
            } else {
                objProxy.realmSet$address((String) json.getString("address"));
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
        if (json.has("latitude")) {
            if (json.isNull("latitude")) {
                objProxy.realmSet$latitude(null);
            } else {
                objProxy.realmSet$latitude((double) json.getDouble("latitude"));
            }
        }
        if (json.has("longitude")) {
            if (json.isNull("longitude")) {
                objProxy.realmSet$longitude(null);
            } else {
                objProxy.realmSet$longitude((double) json.getDouble("longitude"));
            }
        }
        if (json.has("distance")) {
            if (json.isNull("distance")) {
                objProxy.realmSet$distance(null);
            } else {
                objProxy.realmSet$distance((double) json.getDouble("distance"));
            }
        }
        if (json.has("description")) {
            if (json.isNull("description")) {
                objProxy.realmSet$description(null);
            } else {
                objProxy.realmSet$description((String) json.getString("description"));
            }
        }
        if (json.has("type")) {
            if (json.isNull("type")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'type' to null.");
            } else {
                objProxy.realmSet$type((int) json.getInt("type"));
            }
        }
        if (json.has("status")) {
            if (json.isNull("status")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'status' to null.");
            } else {
                objProxy.realmSet$status((int) json.getInt("status"));
            }
        }
        if (json.has("detail")) {
            if (json.isNull("detail")) {
                objProxy.realmSet$detail(null);
            } else {
                objProxy.realmSet$detail((String) json.getString("detail"));
            }
        }
        if (json.has("user")) {
            if (json.isNull("user")) {
                objProxy.realmSet$user(null);
            } else {
                com.directoriodelicias.apps.delicias.classes.User userObj = UserRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("user"), update);
                objProxy.realmSet$user(userObj);
            }
        }
        if (json.has("user_id")) {
            if (json.isNull("user_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'user_id' to null.");
            } else {
                objProxy.realmSet$user_id((int) json.getInt("user_id"));
            }
        }
        if (json.has("imageJson")) {
            if (json.isNull("imageJson")) {
                objProxy.realmSet$imageJson(null);
            } else {
                objProxy.realmSet$imageJson((String) json.getString("imageJson"));
            }
        }
        if (json.has("Voted")) {
            if (json.isNull("Voted")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'Voted' to null.");
            } else {
                objProxy.realmSet$Voted((boolean) json.getBoolean("Voted"));
            }
        }
        if (json.has("votes")) {
            if (json.isNull("votes")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'votes' to null.");
            } else {
                objProxy.realmSet$votes((double) json.getDouble("votes"));
            }
        }
        if (json.has("nbr_votes")) {
            if (json.isNull("nbr_votes")) {
                objProxy.realmSet$nbr_votes(null);
            } else {
                objProxy.realmSet$nbr_votes((String) json.getString("nbr_votes"));
            }
        }
        if (json.has("listImages")) {
            if (json.isNull("listImages")) {
                objProxy.realmSet$listImages(null);
            } else {
                objProxy.realmGet$listImages().clear();
                JSONArray array = json.getJSONArray("listImages");
                for (int i = 0; i < array.length(); i++) {
                    com.directoriodelicias.apps.delicias.classes.Images item = ImagesRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$listImages().add(item);
                }
            }
        }
        if (json.has("phone")) {
            if (json.isNull("phone")) {
                objProxy.realmSet$phone(null);
            } else {
                objProxy.realmSet$phone((String) json.getString("phone"));
            }
        }
        if (json.has("saved")) {
            if (json.isNull("saved")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'saved' to null.");
            } else {
                objProxy.realmSet$saved((int) json.getInt("saved"));
            }
        }
        if (json.has("nbrOffers")) {
            if (json.isNull("nbrOffers")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'nbrOffers' to null.");
            } else {
                objProxy.realmSet$nbrOffers((int) json.getInt("nbrOffers"));
            }
        }
        if (json.has("lastOffer")) {
            if (json.isNull("lastOffer")) {
                objProxy.realmSet$lastOffer(null);
            } else {
                objProxy.realmSet$lastOffer((String) json.getString("lastOffer"));
            }
        }
        if (json.has("category_id")) {
            if (json.isNull("category_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'category_id' to null.");
            } else {
                objProxy.realmSet$category_id((int) json.getInt("category_id"));
            }
        }
        if (json.has("featured")) {
            if (json.isNull("featured")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'featured' to null.");
            } else {
                objProxy.realmSet$featured((int) json.getInt("featured"));
            }
        }
        if (json.has("gallery")) {
            if (json.isNull("gallery")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'gallery' to null.");
            } else {
                objProxy.realmSet$gallery((int) json.getInt("gallery"));
            }
        }
        if (json.has("category_name")) {
            if (json.isNull("category_name")) {
                objProxy.realmSet$category_name(null);
            } else {
                objProxy.realmSet$category_name((String) json.getString("category_name"));
            }
        }
        if (json.has("category_color")) {
            if (json.isNull("category_color")) {
                objProxy.realmSet$category_color(null);
            } else {
                objProxy.realmSet$category_color((String) json.getString("category_color"));
            }
        }
        if (json.has("canChat")) {
            if (json.isNull("canChat")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'canChat' to null.");
            } else {
                objProxy.realmSet$canChat((int) json.getInt("canChat"));
            }
        }
        if (json.has("opening")) {
            if (json.isNull("opening")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'opening' to null.");
            } else {
                objProxy.realmSet$opening((int) json.getInt("opening"));
            }
        }
        if (json.has("opening_time_table")) {
            if (json.isNull("opening_time_table")) {
                objProxy.realmSet$opening_time_table(null);
            } else {
                objProxy.realmSet$opening_time_table((String) json.getString("opening_time_table"));
            }
        }
        if (json.has("opening_time_table_list")) {
            if (json.isNull("opening_time_table_list")) {
                objProxy.realmSet$opening_time_table_list(null);
            } else {
                objProxy.realmGet$opening_time_table_list().clear();
                JSONArray array = json.getJSONArray("opening_time_table_list");
                for (int i = 0; i < array.length(); i++) {
                    com.directoriodelicias.apps.delicias.classes.OpeningTime item = OpeningTimeRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$opening_time_table_list().add(item);
                }
            }
        }
        if (json.has("link")) {
            if (json.isNull("link")) {
                objProxy.realmSet$link(null);
            } else {
                objProxy.realmSet$link((String) json.getString("link"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.directoriodelicias.apps.delicias.classes.Store createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.directoriodelicias.apps.delicias.classes.Store obj = new com.directoriodelicias.apps.delicias.classes.Store();
        final StoreRealmProxyInterface objProxy = (StoreRealmProxyInterface) obj;
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
            } else if (name.equals("name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$name(null);
                }
            } else if (name.equals("address")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$address((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$address(null);
                }
            } else if (name.equals("images")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$images(null);
                } else {
                    com.directoriodelicias.apps.delicias.classes.Images imagesObj = ImagesRealmProxy.createUsingJsonStream(realm, reader);
                    objProxy.realmSet$images(imagesObj);
                }
            } else if (name.equals("latitude")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$latitude((double) reader.nextDouble());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$latitude(null);
                }
            } else if (name.equals("longitude")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$longitude((double) reader.nextDouble());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$longitude(null);
                }
            } else if (name.equals("distance")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$distance((double) reader.nextDouble());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$distance(null);
                }
            } else if (name.equals("description")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$description((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$description(null);
                }
            } else if (name.equals("type")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$type((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'type' to null.");
                }
            } else if (name.equals("status")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$status((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'status' to null.");
                }
            } else if (name.equals("detail")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$detail((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$detail(null);
                }
            } else if (name.equals("user")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$user(null);
                } else {
                    com.directoriodelicias.apps.delicias.classes.User userObj = UserRealmProxy.createUsingJsonStream(realm, reader);
                    objProxy.realmSet$user(userObj);
                }
            } else if (name.equals("user_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$user_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'user_id' to null.");
                }
            } else if (name.equals("imageJson")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$imageJson((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$imageJson(null);
                }
            } else if (name.equals("Voted")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$Voted((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'Voted' to null.");
                }
            } else if (name.equals("votes")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$votes((double) reader.nextDouble());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'votes' to null.");
                }
            } else if (name.equals("nbr_votes")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$nbr_votes((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$nbr_votes(null);
                }
            } else if (name.equals("listImages")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$listImages(null);
                } else {
                    objProxy.realmSet$listImages(new RealmList<com.directoriodelicias.apps.delicias.classes.Images>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.directoriodelicias.apps.delicias.classes.Images item = ImagesRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$listImages().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("phone")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$phone((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$phone(null);
                }
            } else if (name.equals("saved")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$saved((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'saved' to null.");
                }
            } else if (name.equals("nbrOffers")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$nbrOffers((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'nbrOffers' to null.");
                }
            } else if (name.equals("lastOffer")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$lastOffer((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$lastOffer(null);
                }
            } else if (name.equals("category_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$category_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'category_id' to null.");
                }
            } else if (name.equals("featured")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$featured((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'featured' to null.");
                }
            } else if (name.equals("gallery")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$gallery((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'gallery' to null.");
                }
            } else if (name.equals("category_name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$category_name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$category_name(null);
                }
            } else if (name.equals("category_color")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$category_color((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$category_color(null);
                }
            } else if (name.equals("canChat")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$canChat((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'canChat' to null.");
                }
            } else if (name.equals("opening")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$opening((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'opening' to null.");
                }
            } else if (name.equals("opening_time_table")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$opening_time_table((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$opening_time_table(null);
                }
            } else if (name.equals("opening_time_table_list")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$opening_time_table_list(null);
                } else {
                    objProxy.realmSet$opening_time_table_list(new RealmList<com.directoriodelicias.apps.delicias.classes.OpeningTime>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.directoriodelicias.apps.delicias.classes.OpeningTime item = OpeningTimeRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$opening_time_table_list().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("link")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$link((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$link(null);
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

    public static com.directoriodelicias.apps.delicias.classes.Store copyOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Store object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.directoriodelicias.apps.delicias.classes.Store) cachedRealmObject;
        }

        com.directoriodelicias.apps.delicias.classes.Store realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Store.class);
            StoreColumnInfo columnInfo = (StoreColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Store.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((StoreRealmProxyInterface) object).realmGet$id());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Store.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.StoreRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.directoriodelicias.apps.delicias.classes.Store copy(Realm realm, com.directoriodelicias.apps.delicias.classes.Store newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.directoriodelicias.apps.delicias.classes.Store) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.directoriodelicias.apps.delicias.classes.Store realmObject = realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Store.class, ((StoreRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        StoreRealmProxyInterface realmObjectSource = (StoreRealmProxyInterface) newObject;
        StoreRealmProxyInterface realmObjectCopy = (StoreRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$name(realmObjectSource.realmGet$name());
        realmObjectCopy.realmSet$address(realmObjectSource.realmGet$address());

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
        realmObjectCopy.realmSet$latitude(realmObjectSource.realmGet$latitude());
        realmObjectCopy.realmSet$longitude(realmObjectSource.realmGet$longitude());
        realmObjectCopy.realmSet$distance(realmObjectSource.realmGet$distance());
        realmObjectCopy.realmSet$description(realmObjectSource.realmGet$description());
        realmObjectCopy.realmSet$type(realmObjectSource.realmGet$type());
        realmObjectCopy.realmSet$status(realmObjectSource.realmGet$status());
        realmObjectCopy.realmSet$detail(realmObjectSource.realmGet$detail());

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
        realmObjectCopy.realmSet$user_id(realmObjectSource.realmGet$user_id());
        realmObjectCopy.realmSet$imageJson(realmObjectSource.realmGet$imageJson());
        realmObjectCopy.realmSet$Voted(realmObjectSource.realmGet$Voted());
        realmObjectCopy.realmSet$votes(realmObjectSource.realmGet$votes());
        realmObjectCopy.realmSet$nbr_votes(realmObjectSource.realmGet$nbr_votes());

        RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesList = realmObjectSource.realmGet$listImages();
        if (listImagesList != null) {
            RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesRealmList = realmObjectCopy.realmGet$listImages();
            listImagesRealmList.clear();
            for (int i = 0; i < listImagesList.size(); i++) {
                com.directoriodelicias.apps.delicias.classes.Images listImagesItem = listImagesList.get(i);
                com.directoriodelicias.apps.delicias.classes.Images cachelistImages = (com.directoriodelicias.apps.delicias.classes.Images) cache.get(listImagesItem);
                if (cachelistImages != null) {
                    listImagesRealmList.add(cachelistImages);
                } else {
                    listImagesRealmList.add(ImagesRealmProxy.copyOrUpdate(realm, listImagesItem, update, cache));
                }
            }
        }

        realmObjectCopy.realmSet$phone(realmObjectSource.realmGet$phone());
        realmObjectCopy.realmSet$saved(realmObjectSource.realmGet$saved());
        realmObjectCopy.realmSet$nbrOffers(realmObjectSource.realmGet$nbrOffers());
        realmObjectCopy.realmSet$lastOffer(realmObjectSource.realmGet$lastOffer());
        realmObjectCopy.realmSet$category_id(realmObjectSource.realmGet$category_id());
        realmObjectCopy.realmSet$featured(realmObjectSource.realmGet$featured());
        realmObjectCopy.realmSet$gallery(realmObjectSource.realmGet$gallery());
        realmObjectCopy.realmSet$category_name(realmObjectSource.realmGet$category_name());
        realmObjectCopy.realmSet$category_color(realmObjectSource.realmGet$category_color());
        realmObjectCopy.realmSet$canChat(realmObjectSource.realmGet$canChat());
        realmObjectCopy.realmSet$opening(realmObjectSource.realmGet$opening());
        realmObjectCopy.realmSet$opening_time_table(realmObjectSource.realmGet$opening_time_table());

        RealmList<com.directoriodelicias.apps.delicias.classes.OpeningTime> opening_time_table_listList = realmObjectSource.realmGet$opening_time_table_list();
        if (opening_time_table_listList != null) {
            RealmList<com.directoriodelicias.apps.delicias.classes.OpeningTime> opening_time_table_listRealmList = realmObjectCopy.realmGet$opening_time_table_list();
            opening_time_table_listRealmList.clear();
            for (int i = 0; i < opening_time_table_listList.size(); i++) {
                com.directoriodelicias.apps.delicias.classes.OpeningTime opening_time_table_listItem = opening_time_table_listList.get(i);
                com.directoriodelicias.apps.delicias.classes.OpeningTime cacheopening_time_table_list = (com.directoriodelicias.apps.delicias.classes.OpeningTime) cache.get(opening_time_table_listItem);
                if (cacheopening_time_table_list != null) {
                    opening_time_table_listRealmList.add(cacheopening_time_table_list);
                } else {
                    opening_time_table_listRealmList.add(OpeningTimeRealmProxy.copyOrUpdate(realm, opening_time_table_listItem, update, cache));
                }
            }
        }

        realmObjectCopy.realmSet$link(realmObjectSource.realmGet$link());
        return realmObject;
    }

    public static long insert(Realm realm, com.directoriodelicias.apps.delicias.classes.Store object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Store.class);
        long tableNativePtr = table.getNativePtr();
        StoreColumnInfo columnInfo = (StoreColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Store.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((StoreRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((StoreRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((StoreRealmProxyInterface) object).realmGet$id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$name = ((StoreRealmProxyInterface) object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        }
        String realmGet$address = ((StoreRealmProxyInterface) object).realmGet$address();
        if (realmGet$address != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.addressIndex, rowIndex, realmGet$address, false);
        }

        com.directoriodelicias.apps.delicias.classes.Images imagesObj = ((StoreRealmProxyInterface) object).realmGet$images();
        if (imagesObj != null) {
            Long cacheimages = cache.get(imagesObj);
            if (cacheimages == null) {
                cacheimages = ImagesRealmProxy.insert(realm, imagesObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.imagesIndex, rowIndex, cacheimages, false);
        }
        Double realmGet$latitude = ((StoreRealmProxyInterface) object).realmGet$latitude();
        if (realmGet$latitude != null) {
            Table.nativeSetDouble(tableNativePtr, columnInfo.latitudeIndex, rowIndex, realmGet$latitude, false);
        }
        Double realmGet$longitude = ((StoreRealmProxyInterface) object).realmGet$longitude();
        if (realmGet$longitude != null) {
            Table.nativeSetDouble(tableNativePtr, columnInfo.longitudeIndex, rowIndex, realmGet$longitude, false);
        }
        Double realmGet$distance = ((StoreRealmProxyInterface) object).realmGet$distance();
        if (realmGet$distance != null) {
            Table.nativeSetDouble(tableNativePtr, columnInfo.distanceIndex, rowIndex, realmGet$distance, false);
        }
        String realmGet$description = ((StoreRealmProxyInterface) object).realmGet$description();
        if (realmGet$description != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.typeIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$type(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$status(), false);
        String realmGet$detail = ((StoreRealmProxyInterface) object).realmGet$detail();
        if (realmGet$detail != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.detailIndex, rowIndex, realmGet$detail, false);
        }

        com.directoriodelicias.apps.delicias.classes.User userObj = ((StoreRealmProxyInterface) object).realmGet$user();
        if (userObj != null) {
            Long cacheuser = cache.get(userObj);
            if (cacheuser == null) {
                cacheuser = UserRealmProxy.insert(realm, userObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.userIndex, rowIndex, cacheuser, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.user_idIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$user_id(), false);
        String realmGet$imageJson = ((StoreRealmProxyInterface) object).realmGet$imageJson();
        if (realmGet$imageJson != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageJsonIndex, rowIndex, realmGet$imageJson, false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.VotedIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$Voted(), false);
        Table.nativeSetDouble(tableNativePtr, columnInfo.votesIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$votes(), false);
        String realmGet$nbr_votes = ((StoreRealmProxyInterface) object).realmGet$nbr_votes();
        if (realmGet$nbr_votes != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nbr_votesIndex, rowIndex, realmGet$nbr_votes, false);
        }

        RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesList = ((StoreRealmProxyInterface) object).realmGet$listImages();
        if (listImagesList != null) {
            OsList listImagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.listImagesIndex);
            for (com.directoriodelicias.apps.delicias.classes.Images listImagesItem : listImagesList) {
                Long cacheItemIndexlistImages = cache.get(listImagesItem);
                if (cacheItemIndexlistImages == null) {
                    cacheItemIndexlistImages = ImagesRealmProxy.insert(realm, listImagesItem, cache);
                }
                listImagesOsList.addRow(cacheItemIndexlistImages);
            }
        }
        String realmGet$phone = ((StoreRealmProxyInterface) object).realmGet$phone();
        if (realmGet$phone != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.phoneIndex, rowIndex, realmGet$phone, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.savedIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$saved(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.nbrOffersIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$nbrOffers(), false);
        String realmGet$lastOffer = ((StoreRealmProxyInterface) object).realmGet$lastOffer();
        if (realmGet$lastOffer != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.lastOfferIndex, rowIndex, realmGet$lastOffer, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.category_idIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$category_id(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.featuredIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$featured(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.galleryIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$gallery(), false);
        String realmGet$category_name = ((StoreRealmProxyInterface) object).realmGet$category_name();
        if (realmGet$category_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.category_nameIndex, rowIndex, realmGet$category_name, false);
        }
        String realmGet$category_color = ((StoreRealmProxyInterface) object).realmGet$category_color();
        if (realmGet$category_color != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.category_colorIndex, rowIndex, realmGet$category_color, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.canChatIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$canChat(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.openingIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$opening(), false);
        String realmGet$opening_time_table = ((StoreRealmProxyInterface) object).realmGet$opening_time_table();
        if (realmGet$opening_time_table != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.opening_time_tableIndex, rowIndex, realmGet$opening_time_table, false);
        }

        RealmList<com.directoriodelicias.apps.delicias.classes.OpeningTime> opening_time_table_listList = ((StoreRealmProxyInterface) object).realmGet$opening_time_table_list();
        if (opening_time_table_listList != null) {
            OsList opening_time_table_listOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.opening_time_table_listIndex);
            for (com.directoriodelicias.apps.delicias.classes.OpeningTime opening_time_table_listItem : opening_time_table_listList) {
                Long cacheItemIndexopening_time_table_list = cache.get(opening_time_table_listItem);
                if (cacheItemIndexopening_time_table_list == null) {
                    cacheItemIndexopening_time_table_list = OpeningTimeRealmProxy.insert(realm, opening_time_table_listItem, cache);
                }
                opening_time_table_listOsList.addRow(cacheItemIndexopening_time_table_list);
            }
        }
        String realmGet$link = ((StoreRealmProxyInterface) object).realmGet$link();
        if (realmGet$link != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.linkIndex, rowIndex, realmGet$link, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Store.class);
        long tableNativePtr = table.getNativePtr();
        StoreColumnInfo columnInfo = (StoreColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Store.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.Store object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Store) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((StoreRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((StoreRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((StoreRealmProxyInterface) object).realmGet$id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$name = ((StoreRealmProxyInterface) object).realmGet$name();
            if (realmGet$name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
            }
            String realmGet$address = ((StoreRealmProxyInterface) object).realmGet$address();
            if (realmGet$address != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.addressIndex, rowIndex, realmGet$address, false);
            }

            com.directoriodelicias.apps.delicias.classes.Images imagesObj = ((StoreRealmProxyInterface) object).realmGet$images();
            if (imagesObj != null) {
                Long cacheimages = cache.get(imagesObj);
                if (cacheimages == null) {
                    cacheimages = ImagesRealmProxy.insert(realm, imagesObj, cache);
                }
                table.setLink(columnInfo.imagesIndex, rowIndex, cacheimages, false);
            }
            Double realmGet$latitude = ((StoreRealmProxyInterface) object).realmGet$latitude();
            if (realmGet$latitude != null) {
                Table.nativeSetDouble(tableNativePtr, columnInfo.latitudeIndex, rowIndex, realmGet$latitude, false);
            }
            Double realmGet$longitude = ((StoreRealmProxyInterface) object).realmGet$longitude();
            if (realmGet$longitude != null) {
                Table.nativeSetDouble(tableNativePtr, columnInfo.longitudeIndex, rowIndex, realmGet$longitude, false);
            }
            Double realmGet$distance = ((StoreRealmProxyInterface) object).realmGet$distance();
            if (realmGet$distance != null) {
                Table.nativeSetDouble(tableNativePtr, columnInfo.distanceIndex, rowIndex, realmGet$distance, false);
            }
            String realmGet$description = ((StoreRealmProxyInterface) object).realmGet$description();
            if (realmGet$description != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.typeIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$type(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$status(), false);
            String realmGet$detail = ((StoreRealmProxyInterface) object).realmGet$detail();
            if (realmGet$detail != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.detailIndex, rowIndex, realmGet$detail, false);
            }

            com.directoriodelicias.apps.delicias.classes.User userObj = ((StoreRealmProxyInterface) object).realmGet$user();
            if (userObj != null) {
                Long cacheuser = cache.get(userObj);
                if (cacheuser == null) {
                    cacheuser = UserRealmProxy.insert(realm, userObj, cache);
                }
                table.setLink(columnInfo.userIndex, rowIndex, cacheuser, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.user_idIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$user_id(), false);
            String realmGet$imageJson = ((StoreRealmProxyInterface) object).realmGet$imageJson();
            if (realmGet$imageJson != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.imageJsonIndex, rowIndex, realmGet$imageJson, false);
            }
            Table.nativeSetBoolean(tableNativePtr, columnInfo.VotedIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$Voted(), false);
            Table.nativeSetDouble(tableNativePtr, columnInfo.votesIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$votes(), false);
            String realmGet$nbr_votes = ((StoreRealmProxyInterface) object).realmGet$nbr_votes();
            if (realmGet$nbr_votes != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.nbr_votesIndex, rowIndex, realmGet$nbr_votes, false);
            }

            RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesList = ((StoreRealmProxyInterface) object).realmGet$listImages();
            if (listImagesList != null) {
                OsList listImagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.listImagesIndex);
                for (com.directoriodelicias.apps.delicias.classes.Images listImagesItem : listImagesList) {
                    Long cacheItemIndexlistImages = cache.get(listImagesItem);
                    if (cacheItemIndexlistImages == null) {
                        cacheItemIndexlistImages = ImagesRealmProxy.insert(realm, listImagesItem, cache);
                    }
                    listImagesOsList.addRow(cacheItemIndexlistImages);
                }
            }
            String realmGet$phone = ((StoreRealmProxyInterface) object).realmGet$phone();
            if (realmGet$phone != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.phoneIndex, rowIndex, realmGet$phone, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.savedIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$saved(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.nbrOffersIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$nbrOffers(), false);
            String realmGet$lastOffer = ((StoreRealmProxyInterface) object).realmGet$lastOffer();
            if (realmGet$lastOffer != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.lastOfferIndex, rowIndex, realmGet$lastOffer, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.category_idIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$category_id(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.featuredIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$featured(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.galleryIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$gallery(), false);
            String realmGet$category_name = ((StoreRealmProxyInterface) object).realmGet$category_name();
            if (realmGet$category_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.category_nameIndex, rowIndex, realmGet$category_name, false);
            }
            String realmGet$category_color = ((StoreRealmProxyInterface) object).realmGet$category_color();
            if (realmGet$category_color != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.category_colorIndex, rowIndex, realmGet$category_color, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.canChatIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$canChat(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.openingIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$opening(), false);
            String realmGet$opening_time_table = ((StoreRealmProxyInterface) object).realmGet$opening_time_table();
            if (realmGet$opening_time_table != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.opening_time_tableIndex, rowIndex, realmGet$opening_time_table, false);
            }

            RealmList<com.directoriodelicias.apps.delicias.classes.OpeningTime> opening_time_table_listList = ((StoreRealmProxyInterface) object).realmGet$opening_time_table_list();
            if (opening_time_table_listList != null) {
                OsList opening_time_table_listOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.opening_time_table_listIndex);
                for (com.directoriodelicias.apps.delicias.classes.OpeningTime opening_time_table_listItem : opening_time_table_listList) {
                    Long cacheItemIndexopening_time_table_list = cache.get(opening_time_table_listItem);
                    if (cacheItemIndexopening_time_table_list == null) {
                        cacheItemIndexopening_time_table_list = OpeningTimeRealmProxy.insert(realm, opening_time_table_listItem, cache);
                    }
                    opening_time_table_listOsList.addRow(cacheItemIndexopening_time_table_list);
                }
            }
            String realmGet$link = ((StoreRealmProxyInterface) object).realmGet$link();
            if (realmGet$link != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.linkIndex, rowIndex, realmGet$link, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Store object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Store.class);
        long tableNativePtr = table.getNativePtr();
        StoreColumnInfo columnInfo = (StoreColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Store.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((StoreRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((StoreRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((StoreRealmProxyInterface) object).realmGet$id());
        }
        cache.put(object, rowIndex);
        String realmGet$name = ((StoreRealmProxyInterface) object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
        }
        String realmGet$address = ((StoreRealmProxyInterface) object).realmGet$address();
        if (realmGet$address != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.addressIndex, rowIndex, realmGet$address, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.addressIndex, rowIndex, false);
        }

        com.directoriodelicias.apps.delicias.classes.Images imagesObj = ((StoreRealmProxyInterface) object).realmGet$images();
        if (imagesObj != null) {
            Long cacheimages = cache.get(imagesObj);
            if (cacheimages == null) {
                cacheimages = ImagesRealmProxy.insertOrUpdate(realm, imagesObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.imagesIndex, rowIndex, cacheimages, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.imagesIndex, rowIndex);
        }
        Double realmGet$latitude = ((StoreRealmProxyInterface) object).realmGet$latitude();
        if (realmGet$latitude != null) {
            Table.nativeSetDouble(tableNativePtr, columnInfo.latitudeIndex, rowIndex, realmGet$latitude, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.latitudeIndex, rowIndex, false);
        }
        Double realmGet$longitude = ((StoreRealmProxyInterface) object).realmGet$longitude();
        if (realmGet$longitude != null) {
            Table.nativeSetDouble(tableNativePtr, columnInfo.longitudeIndex, rowIndex, realmGet$longitude, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.longitudeIndex, rowIndex, false);
        }
        Double realmGet$distance = ((StoreRealmProxyInterface) object).realmGet$distance();
        if (realmGet$distance != null) {
            Table.nativeSetDouble(tableNativePtr, columnInfo.distanceIndex, rowIndex, realmGet$distance, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.distanceIndex, rowIndex, false);
        }
        String realmGet$description = ((StoreRealmProxyInterface) object).realmGet$description();
        if (realmGet$description != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.descriptionIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.typeIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$type(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$status(), false);
        String realmGet$detail = ((StoreRealmProxyInterface) object).realmGet$detail();
        if (realmGet$detail != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.detailIndex, rowIndex, realmGet$detail, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.detailIndex, rowIndex, false);
        }

        com.directoriodelicias.apps.delicias.classes.User userObj = ((StoreRealmProxyInterface) object).realmGet$user();
        if (userObj != null) {
            Long cacheuser = cache.get(userObj);
            if (cacheuser == null) {
                cacheuser = UserRealmProxy.insertOrUpdate(realm, userObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.userIndex, rowIndex, cacheuser, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.userIndex, rowIndex);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.user_idIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$user_id(), false);
        String realmGet$imageJson = ((StoreRealmProxyInterface) object).realmGet$imageJson();
        if (realmGet$imageJson != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageJsonIndex, rowIndex, realmGet$imageJson, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.imageJsonIndex, rowIndex, false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.VotedIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$Voted(), false);
        Table.nativeSetDouble(tableNativePtr, columnInfo.votesIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$votes(), false);
        String realmGet$nbr_votes = ((StoreRealmProxyInterface) object).realmGet$nbr_votes();
        if (realmGet$nbr_votes != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nbr_votesIndex, rowIndex, realmGet$nbr_votes, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.nbr_votesIndex, rowIndex, false);
        }

        OsList listImagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.listImagesIndex);
        RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesList = ((StoreRealmProxyInterface) object).realmGet$listImages();
        if (listImagesList != null && listImagesList.size() == listImagesOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = listImagesList.size();
            for (int i = 0; i < objects; i++) {
                com.directoriodelicias.apps.delicias.classes.Images listImagesItem = listImagesList.get(i);
                Long cacheItemIndexlistImages = cache.get(listImagesItem);
                if (cacheItemIndexlistImages == null) {
                    cacheItemIndexlistImages = ImagesRealmProxy.insertOrUpdate(realm, listImagesItem, cache);
                }
                listImagesOsList.setRow(i, cacheItemIndexlistImages);
            }
        } else {
            listImagesOsList.removeAll();
            if (listImagesList != null) {
                for (com.directoriodelicias.apps.delicias.classes.Images listImagesItem : listImagesList) {
                    Long cacheItemIndexlistImages = cache.get(listImagesItem);
                    if (cacheItemIndexlistImages == null) {
                        cacheItemIndexlistImages = ImagesRealmProxy.insertOrUpdate(realm, listImagesItem, cache);
                    }
                    listImagesOsList.addRow(cacheItemIndexlistImages);
                }
            }
        }

        String realmGet$phone = ((StoreRealmProxyInterface) object).realmGet$phone();
        if (realmGet$phone != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.phoneIndex, rowIndex, realmGet$phone, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.phoneIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.savedIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$saved(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.nbrOffersIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$nbrOffers(), false);
        String realmGet$lastOffer = ((StoreRealmProxyInterface) object).realmGet$lastOffer();
        if (realmGet$lastOffer != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.lastOfferIndex, rowIndex, realmGet$lastOffer, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.lastOfferIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.category_idIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$category_id(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.featuredIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$featured(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.galleryIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$gallery(), false);
        String realmGet$category_name = ((StoreRealmProxyInterface) object).realmGet$category_name();
        if (realmGet$category_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.category_nameIndex, rowIndex, realmGet$category_name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.category_nameIndex, rowIndex, false);
        }
        String realmGet$category_color = ((StoreRealmProxyInterface) object).realmGet$category_color();
        if (realmGet$category_color != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.category_colorIndex, rowIndex, realmGet$category_color, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.category_colorIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.canChatIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$canChat(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.openingIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$opening(), false);
        String realmGet$opening_time_table = ((StoreRealmProxyInterface) object).realmGet$opening_time_table();
        if (realmGet$opening_time_table != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.opening_time_tableIndex, rowIndex, realmGet$opening_time_table, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.opening_time_tableIndex, rowIndex, false);
        }

        OsList opening_time_table_listOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.opening_time_table_listIndex);
        RealmList<com.directoriodelicias.apps.delicias.classes.OpeningTime> opening_time_table_listList = ((StoreRealmProxyInterface) object).realmGet$opening_time_table_list();
        if (opening_time_table_listList != null && opening_time_table_listList.size() == opening_time_table_listOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = opening_time_table_listList.size();
            for (int i = 0; i < objects; i++) {
                com.directoriodelicias.apps.delicias.classes.OpeningTime opening_time_table_listItem = opening_time_table_listList.get(i);
                Long cacheItemIndexopening_time_table_list = cache.get(opening_time_table_listItem);
                if (cacheItemIndexopening_time_table_list == null) {
                    cacheItemIndexopening_time_table_list = OpeningTimeRealmProxy.insertOrUpdate(realm, opening_time_table_listItem, cache);
                }
                opening_time_table_listOsList.setRow(i, cacheItemIndexopening_time_table_list);
            }
        } else {
            opening_time_table_listOsList.removeAll();
            if (opening_time_table_listList != null) {
                for (com.directoriodelicias.apps.delicias.classes.OpeningTime opening_time_table_listItem : opening_time_table_listList) {
                    Long cacheItemIndexopening_time_table_list = cache.get(opening_time_table_listItem);
                    if (cacheItemIndexopening_time_table_list == null) {
                        cacheItemIndexopening_time_table_list = OpeningTimeRealmProxy.insertOrUpdate(realm, opening_time_table_listItem, cache);
                    }
                    opening_time_table_listOsList.addRow(cacheItemIndexopening_time_table_list);
                }
            }
        }

        String realmGet$link = ((StoreRealmProxyInterface) object).realmGet$link();
        if (realmGet$link != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.linkIndex, rowIndex, realmGet$link, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.linkIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Store.class);
        long tableNativePtr = table.getNativePtr();
        StoreColumnInfo columnInfo = (StoreColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Store.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.Store object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Store) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((StoreRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((StoreRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((StoreRealmProxyInterface) object).realmGet$id());
            }
            cache.put(object, rowIndex);
            String realmGet$name = ((StoreRealmProxyInterface) object).realmGet$name();
            if (realmGet$name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
            }
            String realmGet$address = ((StoreRealmProxyInterface) object).realmGet$address();
            if (realmGet$address != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.addressIndex, rowIndex, realmGet$address, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.addressIndex, rowIndex, false);
            }

            com.directoriodelicias.apps.delicias.classes.Images imagesObj = ((StoreRealmProxyInterface) object).realmGet$images();
            if (imagesObj != null) {
                Long cacheimages = cache.get(imagesObj);
                if (cacheimages == null) {
                    cacheimages = ImagesRealmProxy.insertOrUpdate(realm, imagesObj, cache);
                }
                Table.nativeSetLink(tableNativePtr, columnInfo.imagesIndex, rowIndex, cacheimages, false);
            } else {
                Table.nativeNullifyLink(tableNativePtr, columnInfo.imagesIndex, rowIndex);
            }
            Double realmGet$latitude = ((StoreRealmProxyInterface) object).realmGet$latitude();
            if (realmGet$latitude != null) {
                Table.nativeSetDouble(tableNativePtr, columnInfo.latitudeIndex, rowIndex, realmGet$latitude, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.latitudeIndex, rowIndex, false);
            }
            Double realmGet$longitude = ((StoreRealmProxyInterface) object).realmGet$longitude();
            if (realmGet$longitude != null) {
                Table.nativeSetDouble(tableNativePtr, columnInfo.longitudeIndex, rowIndex, realmGet$longitude, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.longitudeIndex, rowIndex, false);
            }
            Double realmGet$distance = ((StoreRealmProxyInterface) object).realmGet$distance();
            if (realmGet$distance != null) {
                Table.nativeSetDouble(tableNativePtr, columnInfo.distanceIndex, rowIndex, realmGet$distance, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.distanceIndex, rowIndex, false);
            }
            String realmGet$description = ((StoreRealmProxyInterface) object).realmGet$description();
            if (realmGet$description != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.descriptionIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.typeIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$type(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$status(), false);
            String realmGet$detail = ((StoreRealmProxyInterface) object).realmGet$detail();
            if (realmGet$detail != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.detailIndex, rowIndex, realmGet$detail, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.detailIndex, rowIndex, false);
            }

            com.directoriodelicias.apps.delicias.classes.User userObj = ((StoreRealmProxyInterface) object).realmGet$user();
            if (userObj != null) {
                Long cacheuser = cache.get(userObj);
                if (cacheuser == null) {
                    cacheuser = UserRealmProxy.insertOrUpdate(realm, userObj, cache);
                }
                Table.nativeSetLink(tableNativePtr, columnInfo.userIndex, rowIndex, cacheuser, false);
            } else {
                Table.nativeNullifyLink(tableNativePtr, columnInfo.userIndex, rowIndex);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.user_idIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$user_id(), false);
            String realmGet$imageJson = ((StoreRealmProxyInterface) object).realmGet$imageJson();
            if (realmGet$imageJson != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.imageJsonIndex, rowIndex, realmGet$imageJson, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.imageJsonIndex, rowIndex, false);
            }
            Table.nativeSetBoolean(tableNativePtr, columnInfo.VotedIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$Voted(), false);
            Table.nativeSetDouble(tableNativePtr, columnInfo.votesIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$votes(), false);
            String realmGet$nbr_votes = ((StoreRealmProxyInterface) object).realmGet$nbr_votes();
            if (realmGet$nbr_votes != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.nbr_votesIndex, rowIndex, realmGet$nbr_votes, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.nbr_votesIndex, rowIndex, false);
            }

            OsList listImagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.listImagesIndex);
            RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesList = ((StoreRealmProxyInterface) object).realmGet$listImages();
            if (listImagesList != null && listImagesList.size() == listImagesOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = listImagesList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.directoriodelicias.apps.delicias.classes.Images listImagesItem = listImagesList.get(i);
                    Long cacheItemIndexlistImages = cache.get(listImagesItem);
                    if (cacheItemIndexlistImages == null) {
                        cacheItemIndexlistImages = ImagesRealmProxy.insertOrUpdate(realm, listImagesItem, cache);
                    }
                    listImagesOsList.setRow(i, cacheItemIndexlistImages);
                }
            } else {
                listImagesOsList.removeAll();
                if (listImagesList != null) {
                    for (com.directoriodelicias.apps.delicias.classes.Images listImagesItem : listImagesList) {
                        Long cacheItemIndexlistImages = cache.get(listImagesItem);
                        if (cacheItemIndexlistImages == null) {
                            cacheItemIndexlistImages = ImagesRealmProxy.insertOrUpdate(realm, listImagesItem, cache);
                        }
                        listImagesOsList.addRow(cacheItemIndexlistImages);
                    }
                }
            }

            String realmGet$phone = ((StoreRealmProxyInterface) object).realmGet$phone();
            if (realmGet$phone != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.phoneIndex, rowIndex, realmGet$phone, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.phoneIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.savedIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$saved(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.nbrOffersIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$nbrOffers(), false);
            String realmGet$lastOffer = ((StoreRealmProxyInterface) object).realmGet$lastOffer();
            if (realmGet$lastOffer != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.lastOfferIndex, rowIndex, realmGet$lastOffer, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.lastOfferIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.category_idIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$category_id(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.featuredIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$featured(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.galleryIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$gallery(), false);
            String realmGet$category_name = ((StoreRealmProxyInterface) object).realmGet$category_name();
            if (realmGet$category_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.category_nameIndex, rowIndex, realmGet$category_name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.category_nameIndex, rowIndex, false);
            }
            String realmGet$category_color = ((StoreRealmProxyInterface) object).realmGet$category_color();
            if (realmGet$category_color != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.category_colorIndex, rowIndex, realmGet$category_color, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.category_colorIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.canChatIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$canChat(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.openingIndex, rowIndex, ((StoreRealmProxyInterface) object).realmGet$opening(), false);
            String realmGet$opening_time_table = ((StoreRealmProxyInterface) object).realmGet$opening_time_table();
            if (realmGet$opening_time_table != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.opening_time_tableIndex, rowIndex, realmGet$opening_time_table, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.opening_time_tableIndex, rowIndex, false);
            }

            OsList opening_time_table_listOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.opening_time_table_listIndex);
            RealmList<com.directoriodelicias.apps.delicias.classes.OpeningTime> opening_time_table_listList = ((StoreRealmProxyInterface) object).realmGet$opening_time_table_list();
            if (opening_time_table_listList != null && opening_time_table_listList.size() == opening_time_table_listOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = opening_time_table_listList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.directoriodelicias.apps.delicias.classes.OpeningTime opening_time_table_listItem = opening_time_table_listList.get(i);
                    Long cacheItemIndexopening_time_table_list = cache.get(opening_time_table_listItem);
                    if (cacheItemIndexopening_time_table_list == null) {
                        cacheItemIndexopening_time_table_list = OpeningTimeRealmProxy.insertOrUpdate(realm, opening_time_table_listItem, cache);
                    }
                    opening_time_table_listOsList.setRow(i, cacheItemIndexopening_time_table_list);
                }
            } else {
                opening_time_table_listOsList.removeAll();
                if (opening_time_table_listList != null) {
                    for (com.directoriodelicias.apps.delicias.classes.OpeningTime opening_time_table_listItem : opening_time_table_listList) {
                        Long cacheItemIndexopening_time_table_list = cache.get(opening_time_table_listItem);
                        if (cacheItemIndexopening_time_table_list == null) {
                            cacheItemIndexopening_time_table_list = OpeningTimeRealmProxy.insertOrUpdate(realm, opening_time_table_listItem, cache);
                        }
                        opening_time_table_listOsList.addRow(cacheItemIndexopening_time_table_list);
                    }
                }
            }

            String realmGet$link = ((StoreRealmProxyInterface) object).realmGet$link();
            if (realmGet$link != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.linkIndex, rowIndex, realmGet$link, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.linkIndex, rowIndex, false);
            }
        }
    }

    public static com.directoriodelicias.apps.delicias.classes.Store createDetachedCopy(com.directoriodelicias.apps.delicias.classes.Store realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.directoriodelicias.apps.delicias.classes.Store unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.directoriodelicias.apps.delicias.classes.Store();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.directoriodelicias.apps.delicias.classes.Store) cachedObject.object;
            }
            unmanagedObject = (com.directoriodelicias.apps.delicias.classes.Store) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        StoreRealmProxyInterface unmanagedCopy = (StoreRealmProxyInterface) unmanagedObject;
        StoreRealmProxyInterface realmSource = (StoreRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());
        unmanagedCopy.realmSet$name(realmSource.realmGet$name());
        unmanagedCopy.realmSet$address(realmSource.realmGet$address());

        // Deep copy of images
        unmanagedCopy.realmSet$images(ImagesRealmProxy.createDetachedCopy(realmSource.realmGet$images(), currentDepth + 1, maxDepth, cache));
        unmanagedCopy.realmSet$latitude(realmSource.realmGet$latitude());
        unmanagedCopy.realmSet$longitude(realmSource.realmGet$longitude());
        unmanagedCopy.realmSet$distance(realmSource.realmGet$distance());
        unmanagedCopy.realmSet$description(realmSource.realmGet$description());
        unmanagedCopy.realmSet$type(realmSource.realmGet$type());
        unmanagedCopy.realmSet$status(realmSource.realmGet$status());
        unmanagedCopy.realmSet$detail(realmSource.realmGet$detail());

        // Deep copy of user
        unmanagedCopy.realmSet$user(UserRealmProxy.createDetachedCopy(realmSource.realmGet$user(), currentDepth + 1, maxDepth, cache));
        unmanagedCopy.realmSet$user_id(realmSource.realmGet$user_id());
        unmanagedCopy.realmSet$imageJson(realmSource.realmGet$imageJson());
        unmanagedCopy.realmSet$Voted(realmSource.realmGet$Voted());
        unmanagedCopy.realmSet$votes(realmSource.realmGet$votes());
        unmanagedCopy.realmSet$nbr_votes(realmSource.realmGet$nbr_votes());

        // Deep copy of listImages
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$listImages(null);
        } else {
            RealmList<com.directoriodelicias.apps.delicias.classes.Images> managedlistImagesList = realmSource.realmGet$listImages();
            RealmList<com.directoriodelicias.apps.delicias.classes.Images> unmanagedlistImagesList = new RealmList<com.directoriodelicias.apps.delicias.classes.Images>();
            unmanagedCopy.realmSet$listImages(unmanagedlistImagesList);
            int nextDepth = currentDepth + 1;
            int size = managedlistImagesList.size();
            for (int i = 0; i < size; i++) {
                com.directoriodelicias.apps.delicias.classes.Images item = ImagesRealmProxy.createDetachedCopy(managedlistImagesList.get(i), nextDepth, maxDepth, cache);
                unmanagedlistImagesList.add(item);
            }
        }
        unmanagedCopy.realmSet$phone(realmSource.realmGet$phone());
        unmanagedCopy.realmSet$saved(realmSource.realmGet$saved());
        unmanagedCopy.realmSet$nbrOffers(realmSource.realmGet$nbrOffers());
        unmanagedCopy.realmSet$lastOffer(realmSource.realmGet$lastOffer());
        unmanagedCopy.realmSet$category_id(realmSource.realmGet$category_id());
        unmanagedCopy.realmSet$featured(realmSource.realmGet$featured());
        unmanagedCopy.realmSet$gallery(realmSource.realmGet$gallery());
        unmanagedCopy.realmSet$category_name(realmSource.realmGet$category_name());
        unmanagedCopy.realmSet$category_color(realmSource.realmGet$category_color());
        unmanagedCopy.realmSet$canChat(realmSource.realmGet$canChat());
        unmanagedCopy.realmSet$opening(realmSource.realmGet$opening());
        unmanagedCopy.realmSet$opening_time_table(realmSource.realmGet$opening_time_table());

        // Deep copy of opening_time_table_list
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$opening_time_table_list(null);
        } else {
            RealmList<com.directoriodelicias.apps.delicias.classes.OpeningTime> managedopening_time_table_listList = realmSource.realmGet$opening_time_table_list();
            RealmList<com.directoriodelicias.apps.delicias.classes.OpeningTime> unmanagedopening_time_table_listList = new RealmList<com.directoriodelicias.apps.delicias.classes.OpeningTime>();
            unmanagedCopy.realmSet$opening_time_table_list(unmanagedopening_time_table_listList);
            int nextDepth = currentDepth + 1;
            int size = managedopening_time_table_listList.size();
            for (int i = 0; i < size; i++) {
                com.directoriodelicias.apps.delicias.classes.OpeningTime item = OpeningTimeRealmProxy.createDetachedCopy(managedopening_time_table_listList.get(i), nextDepth, maxDepth, cache);
                unmanagedopening_time_table_listList.add(item);
            }
        }
        unmanagedCopy.realmSet$link(realmSource.realmGet$link());

        return unmanagedObject;
    }

    static com.directoriodelicias.apps.delicias.classes.Store update(Realm realm, com.directoriodelicias.apps.delicias.classes.Store realmObject, com.directoriodelicias.apps.delicias.classes.Store newObject, Map<RealmModel, RealmObjectProxy> cache) {
        StoreRealmProxyInterface realmObjectTarget = (StoreRealmProxyInterface) realmObject;
        StoreRealmProxyInterface realmObjectSource = (StoreRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$name(realmObjectSource.realmGet$name());
        realmObjectTarget.realmSet$address(realmObjectSource.realmGet$address());
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
        realmObjectTarget.realmSet$latitude(realmObjectSource.realmGet$latitude());
        realmObjectTarget.realmSet$longitude(realmObjectSource.realmGet$longitude());
        realmObjectTarget.realmSet$distance(realmObjectSource.realmGet$distance());
        realmObjectTarget.realmSet$description(realmObjectSource.realmGet$description());
        realmObjectTarget.realmSet$type(realmObjectSource.realmGet$type());
        realmObjectTarget.realmSet$status(realmObjectSource.realmGet$status());
        realmObjectTarget.realmSet$detail(realmObjectSource.realmGet$detail());
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
        realmObjectTarget.realmSet$user_id(realmObjectSource.realmGet$user_id());
        realmObjectTarget.realmSet$imageJson(realmObjectSource.realmGet$imageJson());
        realmObjectTarget.realmSet$Voted(realmObjectSource.realmGet$Voted());
        realmObjectTarget.realmSet$votes(realmObjectSource.realmGet$votes());
        realmObjectTarget.realmSet$nbr_votes(realmObjectSource.realmGet$nbr_votes());
        RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesList = realmObjectSource.realmGet$listImages();
        RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesRealmList = realmObjectTarget.realmGet$listImages();
        if (listImagesList != null && listImagesList.size() == listImagesRealmList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = listImagesList.size();
            for (int i = 0; i < objects; i++) {
                com.directoriodelicias.apps.delicias.classes.Images listImagesItem = listImagesList.get(i);
                com.directoriodelicias.apps.delicias.classes.Images cachelistImages = (com.directoriodelicias.apps.delicias.classes.Images) cache.get(listImagesItem);
                if (cachelistImages != null) {
                    listImagesRealmList.set(i, cachelistImages);
                } else {
                    listImagesRealmList.set(i, ImagesRealmProxy.copyOrUpdate(realm, listImagesItem, true, cache));
                }
            }
        } else {
            listImagesRealmList.clear();
            if (listImagesList != null) {
                for (int i = 0; i < listImagesList.size(); i++) {
                    com.directoriodelicias.apps.delicias.classes.Images listImagesItem = listImagesList.get(i);
                    com.directoriodelicias.apps.delicias.classes.Images cachelistImages = (com.directoriodelicias.apps.delicias.classes.Images) cache.get(listImagesItem);
                    if (cachelistImages != null) {
                        listImagesRealmList.add(cachelistImages);
                    } else {
                        listImagesRealmList.add(ImagesRealmProxy.copyOrUpdate(realm, listImagesItem, true, cache));
                    }
                }
            }
        }
        realmObjectTarget.realmSet$phone(realmObjectSource.realmGet$phone());
        realmObjectTarget.realmSet$saved(realmObjectSource.realmGet$saved());
        realmObjectTarget.realmSet$nbrOffers(realmObjectSource.realmGet$nbrOffers());
        realmObjectTarget.realmSet$lastOffer(realmObjectSource.realmGet$lastOffer());
        realmObjectTarget.realmSet$category_id(realmObjectSource.realmGet$category_id());
        realmObjectTarget.realmSet$featured(realmObjectSource.realmGet$featured());
        realmObjectTarget.realmSet$gallery(realmObjectSource.realmGet$gallery());
        realmObjectTarget.realmSet$category_name(realmObjectSource.realmGet$category_name());
        realmObjectTarget.realmSet$category_color(realmObjectSource.realmGet$category_color());
        realmObjectTarget.realmSet$canChat(realmObjectSource.realmGet$canChat());
        realmObjectTarget.realmSet$opening(realmObjectSource.realmGet$opening());
        realmObjectTarget.realmSet$opening_time_table(realmObjectSource.realmGet$opening_time_table());
        RealmList<com.directoriodelicias.apps.delicias.classes.OpeningTime> opening_time_table_listList = realmObjectSource.realmGet$opening_time_table_list();
        RealmList<com.directoriodelicias.apps.delicias.classes.OpeningTime> opening_time_table_listRealmList = realmObjectTarget.realmGet$opening_time_table_list();
        if (opening_time_table_listList != null && opening_time_table_listList.size() == opening_time_table_listRealmList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = opening_time_table_listList.size();
            for (int i = 0; i < objects; i++) {
                com.directoriodelicias.apps.delicias.classes.OpeningTime opening_time_table_listItem = opening_time_table_listList.get(i);
                com.directoriodelicias.apps.delicias.classes.OpeningTime cacheopening_time_table_list = (com.directoriodelicias.apps.delicias.classes.OpeningTime) cache.get(opening_time_table_listItem);
                if (cacheopening_time_table_list != null) {
                    opening_time_table_listRealmList.set(i, cacheopening_time_table_list);
                } else {
                    opening_time_table_listRealmList.set(i, OpeningTimeRealmProxy.copyOrUpdate(realm, opening_time_table_listItem, true, cache));
                }
            }
        } else {
            opening_time_table_listRealmList.clear();
            if (opening_time_table_listList != null) {
                for (int i = 0; i < opening_time_table_listList.size(); i++) {
                    com.directoriodelicias.apps.delicias.classes.OpeningTime opening_time_table_listItem = opening_time_table_listList.get(i);
                    com.directoriodelicias.apps.delicias.classes.OpeningTime cacheopening_time_table_list = (com.directoriodelicias.apps.delicias.classes.OpeningTime) cache.get(opening_time_table_listItem);
                    if (cacheopening_time_table_list != null) {
                        opening_time_table_listRealmList.add(cacheopening_time_table_list);
                    } else {
                        opening_time_table_listRealmList.add(OpeningTimeRealmProxy.copyOrUpdate(realm, opening_time_table_listItem, true, cache));
                    }
                }
            }
        }
        realmObjectTarget.realmSet$link(realmObjectSource.realmGet$link());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Store = proxy[");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{name:");
        stringBuilder.append(realmGet$name() != null ? realmGet$name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{address:");
        stringBuilder.append(realmGet$address() != null ? realmGet$address() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{images:");
        stringBuilder.append(realmGet$images() != null ? "Images" : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{latitude:");
        stringBuilder.append(realmGet$latitude() != null ? realmGet$latitude() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{longitude:");
        stringBuilder.append(realmGet$longitude() != null ? realmGet$longitude() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{distance:");
        stringBuilder.append(realmGet$distance() != null ? realmGet$distance() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{description:");
        stringBuilder.append(realmGet$description() != null ? realmGet$description() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{type:");
        stringBuilder.append(realmGet$type());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{status:");
        stringBuilder.append(realmGet$status());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{detail:");
        stringBuilder.append(realmGet$detail() != null ? realmGet$detail() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{user:");
        stringBuilder.append(realmGet$user() != null ? "User" : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{user_id:");
        stringBuilder.append(realmGet$user_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{imageJson:");
        stringBuilder.append(realmGet$imageJson() != null ? realmGet$imageJson() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{Voted:");
        stringBuilder.append(realmGet$Voted());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{votes:");
        stringBuilder.append(realmGet$votes());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{nbr_votes:");
        stringBuilder.append(realmGet$nbr_votes() != null ? realmGet$nbr_votes() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{listImages:");
        stringBuilder.append("RealmList<Images>[").append(realmGet$listImages().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{phone:");
        stringBuilder.append(realmGet$phone() != null ? realmGet$phone() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{saved:");
        stringBuilder.append(realmGet$saved());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{nbrOffers:");
        stringBuilder.append(realmGet$nbrOffers());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{lastOffer:");
        stringBuilder.append(realmGet$lastOffer() != null ? realmGet$lastOffer() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{category_id:");
        stringBuilder.append(realmGet$category_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{featured:");
        stringBuilder.append(realmGet$featured());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{gallery:");
        stringBuilder.append(realmGet$gallery());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{category_name:");
        stringBuilder.append(realmGet$category_name() != null ? realmGet$category_name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{category_color:");
        stringBuilder.append(realmGet$category_color() != null ? realmGet$category_color() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{canChat:");
        stringBuilder.append(realmGet$canChat());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{opening:");
        stringBuilder.append(realmGet$opening());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{opening_time_table:");
        stringBuilder.append(realmGet$opening_time_table() != null ? realmGet$opening_time_table() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{opening_time_table_list:");
        stringBuilder.append("RealmList<OpeningTime>[").append(realmGet$opening_time_table_list().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{link:");
        stringBuilder.append(realmGet$link() != null ? realmGet$link() : "null");
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
        StoreRealmProxy aStore = (StoreRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aStore.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aStore.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aStore.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
