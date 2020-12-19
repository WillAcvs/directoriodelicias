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
public class EventRealmProxy extends com.directoriodelicias.apps.delicias.classes.Event
    implements RealmObjectProxy, EventRealmProxyInterface {

    static final class EventColumnInfo extends ColumnInfo {
        long statusIndex;
        long idIndex;
        long nameIndex;
        long addressIndex;
        long ImagesIndex;
        long imageJsonIndex;
        long dateBIndex;
        long dateEIndex;
        long descriptionIndex;
        long distanceIndex;
        long latIndex;
        long lngIndex;
        long telIndex;
        long webSiteIndex;
        long typeIndex;
        long likedIndex;
        long store_idIndex;
        long store_nameIndex;
        long linkIndex;
        long savedIndex;
        long featuredIndex;
        long listImagesIndex;

        EventColumnInfo(OsSchemaInfo schemaInfo) {
            super(22);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("Event");
            this.statusIndex = addColumnDetails("status", objectSchemaInfo);
            this.idIndex = addColumnDetails("id", objectSchemaInfo);
            this.nameIndex = addColumnDetails("name", objectSchemaInfo);
            this.addressIndex = addColumnDetails("address", objectSchemaInfo);
            this.ImagesIndex = addColumnDetails("Images", objectSchemaInfo);
            this.imageJsonIndex = addColumnDetails("imageJson", objectSchemaInfo);
            this.dateBIndex = addColumnDetails("dateB", objectSchemaInfo);
            this.dateEIndex = addColumnDetails("dateE", objectSchemaInfo);
            this.descriptionIndex = addColumnDetails("description", objectSchemaInfo);
            this.distanceIndex = addColumnDetails("distance", objectSchemaInfo);
            this.latIndex = addColumnDetails("lat", objectSchemaInfo);
            this.lngIndex = addColumnDetails("lng", objectSchemaInfo);
            this.telIndex = addColumnDetails("tel", objectSchemaInfo);
            this.webSiteIndex = addColumnDetails("webSite", objectSchemaInfo);
            this.typeIndex = addColumnDetails("type", objectSchemaInfo);
            this.likedIndex = addColumnDetails("liked", objectSchemaInfo);
            this.store_idIndex = addColumnDetails("store_id", objectSchemaInfo);
            this.store_nameIndex = addColumnDetails("store_name", objectSchemaInfo);
            this.linkIndex = addColumnDetails("link", objectSchemaInfo);
            this.savedIndex = addColumnDetails("saved", objectSchemaInfo);
            this.featuredIndex = addColumnDetails("featured", objectSchemaInfo);
            this.listImagesIndex = addColumnDetails("listImages", objectSchemaInfo);
        }

        EventColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new EventColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final EventColumnInfo src = (EventColumnInfo) rawSrc;
            final EventColumnInfo dst = (EventColumnInfo) rawDst;
            dst.statusIndex = src.statusIndex;
            dst.idIndex = src.idIndex;
            dst.nameIndex = src.nameIndex;
            dst.addressIndex = src.addressIndex;
            dst.ImagesIndex = src.ImagesIndex;
            dst.imageJsonIndex = src.imageJsonIndex;
            dst.dateBIndex = src.dateBIndex;
            dst.dateEIndex = src.dateEIndex;
            dst.descriptionIndex = src.descriptionIndex;
            dst.distanceIndex = src.distanceIndex;
            dst.latIndex = src.latIndex;
            dst.lngIndex = src.lngIndex;
            dst.telIndex = src.telIndex;
            dst.webSiteIndex = src.webSiteIndex;
            dst.typeIndex = src.typeIndex;
            dst.likedIndex = src.likedIndex;
            dst.store_idIndex = src.store_idIndex;
            dst.store_nameIndex = src.store_nameIndex;
            dst.linkIndex = src.linkIndex;
            dst.savedIndex = src.savedIndex;
            dst.featuredIndex = src.featuredIndex;
            dst.listImagesIndex = src.listImagesIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(22);
        fieldNames.add("status");
        fieldNames.add("id");
        fieldNames.add("name");
        fieldNames.add("address");
        fieldNames.add("Images");
        fieldNames.add("imageJson");
        fieldNames.add("dateB");
        fieldNames.add("dateE");
        fieldNames.add("description");
        fieldNames.add("distance");
        fieldNames.add("lat");
        fieldNames.add("lng");
        fieldNames.add("tel");
        fieldNames.add("webSite");
        fieldNames.add("type");
        fieldNames.add("liked");
        fieldNames.add("store_id");
        fieldNames.add("store_name");
        fieldNames.add("link");
        fieldNames.add("saved");
        fieldNames.add("featured");
        fieldNames.add("listImages");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private EventColumnInfo columnInfo;
    private ProxyState<com.directoriodelicias.apps.delicias.classes.Event> proxyState;
    private RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesRealmList;

    EventRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (EventColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.directoriodelicias.apps.delicias.classes.Event>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
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
    public com.directoriodelicias.apps.delicias.classes.Images realmGet$Images() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.ImagesIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(com.directoriodelicias.apps.delicias.classes.Images.class, proxyState.getRow$realm().getLink(columnInfo.ImagesIndex), false, Collections.<String>emptyList());
    }

    @Override
    public void realmSet$Images(com.directoriodelicias.apps.delicias.classes.Images value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("Images")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.ImagesIndex);
                return;
            }
            proxyState.checkValidObject(value);
            row.getTable().setLink(columnInfo.ImagesIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.ImagesIndex);
            return;
        }
        proxyState.checkValidObject(value);
        proxyState.getRow$realm().setLink(columnInfo.ImagesIndex, ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex());
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
    public String realmGet$dateB() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.dateBIndex);
    }

    @Override
    public void realmSet$dateB(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.dateBIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.dateBIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.dateBIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.dateBIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$dateE() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.dateEIndex);
    }

    @Override
    public void realmSet$dateE(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.dateEIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.dateEIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.dateEIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.dateEIndex, value);
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
    public Double realmGet$lat() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.latIndex)) {
            return null;
        }
        return (double) proxyState.getRow$realm().getDouble(columnInfo.latIndex);
    }

    @Override
    public void realmSet$lat(Double value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.latIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setDouble(columnInfo.latIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.latIndex);
            return;
        }
        proxyState.getRow$realm().setDouble(columnInfo.latIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Double realmGet$lng() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.lngIndex)) {
            return null;
        }
        return (double) proxyState.getRow$realm().getDouble(columnInfo.lngIndex);
    }

    @Override
    public void realmSet$lng(Double value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.lngIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setDouble(columnInfo.lngIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.lngIndex);
            return;
        }
        proxyState.getRow$realm().setDouble(columnInfo.lngIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$tel() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.telIndex);
    }

    @Override
    public void realmSet$tel(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.telIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.telIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.telIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.telIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$webSite() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.webSiteIndex);
    }

    @Override
    public void realmSet$webSite(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.webSiteIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.webSiteIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.webSiteIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.webSiteIndex, value);
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
    public boolean realmGet$liked() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.likedIndex);
    }

    @Override
    public void realmSet$liked(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.likedIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.likedIndex, value);
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
    public String realmGet$store_name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.store_nameIndex);
    }

    @Override
    public void realmSet$store_name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.store_nameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.store_nameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.store_nameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.store_nameIndex, value);
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

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("Event", 22, 0);
        builder.addPersistedProperty("status", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("address", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedLinkProperty("Images", RealmFieldType.OBJECT, "Images");
        builder.addPersistedProperty("imageJson", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("dateB", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("dateE", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("description", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("distance", RealmFieldType.DOUBLE, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("lat", RealmFieldType.DOUBLE, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("lng", RealmFieldType.DOUBLE, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("tel", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("webSite", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("type", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("liked", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("store_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("store_name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("link", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("saved", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("featured", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedLinkProperty("listImages", RealmFieldType.LIST, "Images");
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static EventColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new EventColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "Event";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.directoriodelicias.apps.delicias.classes.Event createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(2);
        com.directoriodelicias.apps.delicias.classes.Event obj = null;
        if (update) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Event.class);
            EventColumnInfo columnInfo = (EventColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Event.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Event.class), false, Collections.<String> emptyList());
                    obj = new io.realm.EventRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("Images")) {
                excludeFields.add("Images");
            }
            if (json.has("listImages")) {
                excludeFields.add("listImages");
            }
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.EventRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Event.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.EventRealmProxy) realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Event.class, json.getInt("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }

        final EventRealmProxyInterface objProxy = (EventRealmProxyInterface) obj;
        if (json.has("status")) {
            if (json.isNull("status")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'status' to null.");
            } else {
                objProxy.realmSet$status((int) json.getInt("status"));
            }
        }
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
        if (json.has("Images")) {
            if (json.isNull("Images")) {
                objProxy.realmSet$Images(null);
            } else {
                com.directoriodelicias.apps.delicias.classes.Images ImagesObj = ImagesRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("Images"), update);
                objProxy.realmSet$Images(ImagesObj);
            }
        }
        if (json.has("imageJson")) {
            if (json.isNull("imageJson")) {
                objProxy.realmSet$imageJson(null);
            } else {
                objProxy.realmSet$imageJson((String) json.getString("imageJson"));
            }
        }
        if (json.has("dateB")) {
            if (json.isNull("dateB")) {
                objProxy.realmSet$dateB(null);
            } else {
                objProxy.realmSet$dateB((String) json.getString("dateB"));
            }
        }
        if (json.has("dateE")) {
            if (json.isNull("dateE")) {
                objProxy.realmSet$dateE(null);
            } else {
                objProxy.realmSet$dateE((String) json.getString("dateE"));
            }
        }
        if (json.has("description")) {
            if (json.isNull("description")) {
                objProxy.realmSet$description(null);
            } else {
                objProxy.realmSet$description((String) json.getString("description"));
            }
        }
        if (json.has("distance")) {
            if (json.isNull("distance")) {
                objProxy.realmSet$distance(null);
            } else {
                objProxy.realmSet$distance((double) json.getDouble("distance"));
            }
        }
        if (json.has("lat")) {
            if (json.isNull("lat")) {
                objProxy.realmSet$lat(null);
            } else {
                objProxy.realmSet$lat((double) json.getDouble("lat"));
            }
        }
        if (json.has("lng")) {
            if (json.isNull("lng")) {
                objProxy.realmSet$lng(null);
            } else {
                objProxy.realmSet$lng((double) json.getDouble("lng"));
            }
        }
        if (json.has("tel")) {
            if (json.isNull("tel")) {
                objProxy.realmSet$tel(null);
            } else {
                objProxy.realmSet$tel((String) json.getString("tel"));
            }
        }
        if (json.has("webSite")) {
            if (json.isNull("webSite")) {
                objProxy.realmSet$webSite(null);
            } else {
                objProxy.realmSet$webSite((String) json.getString("webSite"));
            }
        }
        if (json.has("type")) {
            if (json.isNull("type")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'type' to null.");
            } else {
                objProxy.realmSet$type((int) json.getInt("type"));
            }
        }
        if (json.has("liked")) {
            if (json.isNull("liked")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'liked' to null.");
            } else {
                objProxy.realmSet$liked((boolean) json.getBoolean("liked"));
            }
        }
        if (json.has("store_id")) {
            if (json.isNull("store_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'store_id' to null.");
            } else {
                objProxy.realmSet$store_id((int) json.getInt("store_id"));
            }
        }
        if (json.has("store_name")) {
            if (json.isNull("store_name")) {
                objProxy.realmSet$store_name(null);
            } else {
                objProxy.realmSet$store_name((String) json.getString("store_name"));
            }
        }
        if (json.has("link")) {
            if (json.isNull("link")) {
                objProxy.realmSet$link(null);
            } else {
                objProxy.realmSet$link((String) json.getString("link"));
            }
        }
        if (json.has("saved")) {
            if (json.isNull("saved")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'saved' to null.");
            } else {
                objProxy.realmSet$saved((int) json.getInt("saved"));
            }
        }
        if (json.has("featured")) {
            if (json.isNull("featured")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'featured' to null.");
            } else {
                objProxy.realmSet$featured((int) json.getInt("featured"));
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
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.directoriodelicias.apps.delicias.classes.Event createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.directoriodelicias.apps.delicias.classes.Event obj = new com.directoriodelicias.apps.delicias.classes.Event();
        final EventRealmProxyInterface objProxy = (EventRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("status")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$status((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'status' to null.");
                }
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
            } else if (name.equals("Images")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$Images(null);
                } else {
                    com.directoriodelicias.apps.delicias.classes.Images ImagesObj = ImagesRealmProxy.createUsingJsonStream(realm, reader);
                    objProxy.realmSet$Images(ImagesObj);
                }
            } else if (name.equals("imageJson")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$imageJson((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$imageJson(null);
                }
            } else if (name.equals("dateB")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$dateB((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$dateB(null);
                }
            } else if (name.equals("dateE")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$dateE((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$dateE(null);
                }
            } else if (name.equals("description")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$description((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$description(null);
                }
            } else if (name.equals("distance")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$distance((double) reader.nextDouble());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$distance(null);
                }
            } else if (name.equals("lat")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$lat((double) reader.nextDouble());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$lat(null);
                }
            } else if (name.equals("lng")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$lng((double) reader.nextDouble());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$lng(null);
                }
            } else if (name.equals("tel")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$tel((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$tel(null);
                }
            } else if (name.equals("webSite")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$webSite((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$webSite(null);
                }
            } else if (name.equals("type")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$type((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'type' to null.");
                }
            } else if (name.equals("liked")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$liked((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'liked' to null.");
                }
            } else if (name.equals("store_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$store_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'store_id' to null.");
                }
            } else if (name.equals("store_name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$store_name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$store_name(null);
                }
            } else if (name.equals("link")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$link((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$link(null);
                }
            } else if (name.equals("saved")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$saved((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'saved' to null.");
                }
            } else if (name.equals("featured")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$featured((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'featured' to null.");
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

    public static com.directoriodelicias.apps.delicias.classes.Event copyOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Event object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.directoriodelicias.apps.delicias.classes.Event) cachedRealmObject;
        }

        com.directoriodelicias.apps.delicias.classes.Event realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Event.class);
            EventColumnInfo columnInfo = (EventColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Event.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((EventRealmProxyInterface) object).realmGet$id());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Event.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.EventRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.directoriodelicias.apps.delicias.classes.Event copy(Realm realm, com.directoriodelicias.apps.delicias.classes.Event newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.directoriodelicias.apps.delicias.classes.Event) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.directoriodelicias.apps.delicias.classes.Event realmObject = realm.createObjectInternal(com.directoriodelicias.apps.delicias.classes.Event.class, ((EventRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        EventRealmProxyInterface realmObjectSource = (EventRealmProxyInterface) newObject;
        EventRealmProxyInterface realmObjectCopy = (EventRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$status(realmObjectSource.realmGet$status());
        realmObjectCopy.realmSet$name(realmObjectSource.realmGet$name());
        realmObjectCopy.realmSet$address(realmObjectSource.realmGet$address());

        com.directoriodelicias.apps.delicias.classes.Images ImagesObj = realmObjectSource.realmGet$Images();
        if (ImagesObj == null) {
            realmObjectCopy.realmSet$Images(null);
        } else {
            com.directoriodelicias.apps.delicias.classes.Images cacheImages = (com.directoriodelicias.apps.delicias.classes.Images) cache.get(ImagesObj);
            if (cacheImages != null) {
                realmObjectCopy.realmSet$Images(cacheImages);
            } else {
                realmObjectCopy.realmSet$Images(ImagesRealmProxy.copyOrUpdate(realm, ImagesObj, update, cache));
            }
        }
        realmObjectCopy.realmSet$imageJson(realmObjectSource.realmGet$imageJson());
        realmObjectCopy.realmSet$dateB(realmObjectSource.realmGet$dateB());
        realmObjectCopy.realmSet$dateE(realmObjectSource.realmGet$dateE());
        realmObjectCopy.realmSet$description(realmObjectSource.realmGet$description());
        realmObjectCopy.realmSet$distance(realmObjectSource.realmGet$distance());
        realmObjectCopy.realmSet$lat(realmObjectSource.realmGet$lat());
        realmObjectCopy.realmSet$lng(realmObjectSource.realmGet$lng());
        realmObjectCopy.realmSet$tel(realmObjectSource.realmGet$tel());
        realmObjectCopy.realmSet$webSite(realmObjectSource.realmGet$webSite());
        realmObjectCopy.realmSet$type(realmObjectSource.realmGet$type());
        realmObjectCopy.realmSet$liked(realmObjectSource.realmGet$liked());
        realmObjectCopy.realmSet$store_id(realmObjectSource.realmGet$store_id());
        realmObjectCopy.realmSet$store_name(realmObjectSource.realmGet$store_name());
        realmObjectCopy.realmSet$link(realmObjectSource.realmGet$link());
        realmObjectCopy.realmSet$saved(realmObjectSource.realmGet$saved());
        realmObjectCopy.realmSet$featured(realmObjectSource.realmGet$featured());

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

        return realmObject;
    }

    public static long insert(Realm realm, com.directoriodelicias.apps.delicias.classes.Event object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Event.class);
        long tableNativePtr = table.getNativePtr();
        EventColumnInfo columnInfo = (EventColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Event.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((EventRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((EventRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((EventRealmProxyInterface) object).realmGet$id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$status(), false);
        String realmGet$name = ((EventRealmProxyInterface) object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        }
        String realmGet$address = ((EventRealmProxyInterface) object).realmGet$address();
        if (realmGet$address != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.addressIndex, rowIndex, realmGet$address, false);
        }

        com.directoriodelicias.apps.delicias.classes.Images ImagesObj = ((EventRealmProxyInterface) object).realmGet$Images();
        if (ImagesObj != null) {
            Long cacheImages = cache.get(ImagesObj);
            if (cacheImages == null) {
                cacheImages = ImagesRealmProxy.insert(realm, ImagesObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.ImagesIndex, rowIndex, cacheImages, false);
        }
        String realmGet$imageJson = ((EventRealmProxyInterface) object).realmGet$imageJson();
        if (realmGet$imageJson != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageJsonIndex, rowIndex, realmGet$imageJson, false);
        }
        String realmGet$dateB = ((EventRealmProxyInterface) object).realmGet$dateB();
        if (realmGet$dateB != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dateBIndex, rowIndex, realmGet$dateB, false);
        }
        String realmGet$dateE = ((EventRealmProxyInterface) object).realmGet$dateE();
        if (realmGet$dateE != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dateEIndex, rowIndex, realmGet$dateE, false);
        }
        String realmGet$description = ((EventRealmProxyInterface) object).realmGet$description();
        if (realmGet$description != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
        }
        Double realmGet$distance = ((EventRealmProxyInterface) object).realmGet$distance();
        if (realmGet$distance != null) {
            Table.nativeSetDouble(tableNativePtr, columnInfo.distanceIndex, rowIndex, realmGet$distance, false);
        }
        Double realmGet$lat = ((EventRealmProxyInterface) object).realmGet$lat();
        if (realmGet$lat != null) {
            Table.nativeSetDouble(tableNativePtr, columnInfo.latIndex, rowIndex, realmGet$lat, false);
        }
        Double realmGet$lng = ((EventRealmProxyInterface) object).realmGet$lng();
        if (realmGet$lng != null) {
            Table.nativeSetDouble(tableNativePtr, columnInfo.lngIndex, rowIndex, realmGet$lng, false);
        }
        String realmGet$tel = ((EventRealmProxyInterface) object).realmGet$tel();
        if (realmGet$tel != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.telIndex, rowIndex, realmGet$tel, false);
        }
        String realmGet$webSite = ((EventRealmProxyInterface) object).realmGet$webSite();
        if (realmGet$webSite != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.webSiteIndex, rowIndex, realmGet$webSite, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.typeIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$type(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.likedIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$liked(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.store_idIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$store_id(), false);
        String realmGet$store_name = ((EventRealmProxyInterface) object).realmGet$store_name();
        if (realmGet$store_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.store_nameIndex, rowIndex, realmGet$store_name, false);
        }
        String realmGet$link = ((EventRealmProxyInterface) object).realmGet$link();
        if (realmGet$link != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.linkIndex, rowIndex, realmGet$link, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.savedIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$saved(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.featuredIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$featured(), false);

        RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesList = ((EventRealmProxyInterface) object).realmGet$listImages();
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
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Event.class);
        long tableNativePtr = table.getNativePtr();
        EventColumnInfo columnInfo = (EventColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Event.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.Event object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Event) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((EventRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((EventRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((EventRealmProxyInterface) object).realmGet$id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$status(), false);
            String realmGet$name = ((EventRealmProxyInterface) object).realmGet$name();
            if (realmGet$name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
            }
            String realmGet$address = ((EventRealmProxyInterface) object).realmGet$address();
            if (realmGet$address != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.addressIndex, rowIndex, realmGet$address, false);
            }

            com.directoriodelicias.apps.delicias.classes.Images ImagesObj = ((EventRealmProxyInterface) object).realmGet$Images();
            if (ImagesObj != null) {
                Long cacheImages = cache.get(ImagesObj);
                if (cacheImages == null) {
                    cacheImages = ImagesRealmProxy.insert(realm, ImagesObj, cache);
                }
                table.setLink(columnInfo.ImagesIndex, rowIndex, cacheImages, false);
            }
            String realmGet$imageJson = ((EventRealmProxyInterface) object).realmGet$imageJson();
            if (realmGet$imageJson != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.imageJsonIndex, rowIndex, realmGet$imageJson, false);
            }
            String realmGet$dateB = ((EventRealmProxyInterface) object).realmGet$dateB();
            if (realmGet$dateB != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dateBIndex, rowIndex, realmGet$dateB, false);
            }
            String realmGet$dateE = ((EventRealmProxyInterface) object).realmGet$dateE();
            if (realmGet$dateE != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dateEIndex, rowIndex, realmGet$dateE, false);
            }
            String realmGet$description = ((EventRealmProxyInterface) object).realmGet$description();
            if (realmGet$description != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
            }
            Double realmGet$distance = ((EventRealmProxyInterface) object).realmGet$distance();
            if (realmGet$distance != null) {
                Table.nativeSetDouble(tableNativePtr, columnInfo.distanceIndex, rowIndex, realmGet$distance, false);
            }
            Double realmGet$lat = ((EventRealmProxyInterface) object).realmGet$lat();
            if (realmGet$lat != null) {
                Table.nativeSetDouble(tableNativePtr, columnInfo.latIndex, rowIndex, realmGet$lat, false);
            }
            Double realmGet$lng = ((EventRealmProxyInterface) object).realmGet$lng();
            if (realmGet$lng != null) {
                Table.nativeSetDouble(tableNativePtr, columnInfo.lngIndex, rowIndex, realmGet$lng, false);
            }
            String realmGet$tel = ((EventRealmProxyInterface) object).realmGet$tel();
            if (realmGet$tel != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.telIndex, rowIndex, realmGet$tel, false);
            }
            String realmGet$webSite = ((EventRealmProxyInterface) object).realmGet$webSite();
            if (realmGet$webSite != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.webSiteIndex, rowIndex, realmGet$webSite, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.typeIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$type(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.likedIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$liked(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.store_idIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$store_id(), false);
            String realmGet$store_name = ((EventRealmProxyInterface) object).realmGet$store_name();
            if (realmGet$store_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.store_nameIndex, rowIndex, realmGet$store_name, false);
            }
            String realmGet$link = ((EventRealmProxyInterface) object).realmGet$link();
            if (realmGet$link != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.linkIndex, rowIndex, realmGet$link, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.savedIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$saved(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.featuredIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$featured(), false);

            RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesList = ((EventRealmProxyInterface) object).realmGet$listImages();
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
        }
    }

    public static long insertOrUpdate(Realm realm, com.directoriodelicias.apps.delicias.classes.Event object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Event.class);
        long tableNativePtr = table.getNativePtr();
        EventColumnInfo columnInfo = (EventColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Event.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((EventRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((EventRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((EventRealmProxyInterface) object).realmGet$id());
        }
        cache.put(object, rowIndex);
        Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$status(), false);
        String realmGet$name = ((EventRealmProxyInterface) object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
        }
        String realmGet$address = ((EventRealmProxyInterface) object).realmGet$address();
        if (realmGet$address != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.addressIndex, rowIndex, realmGet$address, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.addressIndex, rowIndex, false);
        }

        com.directoriodelicias.apps.delicias.classes.Images ImagesObj = ((EventRealmProxyInterface) object).realmGet$Images();
        if (ImagesObj != null) {
            Long cacheImages = cache.get(ImagesObj);
            if (cacheImages == null) {
                cacheImages = ImagesRealmProxy.insertOrUpdate(realm, ImagesObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.ImagesIndex, rowIndex, cacheImages, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.ImagesIndex, rowIndex);
        }
        String realmGet$imageJson = ((EventRealmProxyInterface) object).realmGet$imageJson();
        if (realmGet$imageJson != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageJsonIndex, rowIndex, realmGet$imageJson, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.imageJsonIndex, rowIndex, false);
        }
        String realmGet$dateB = ((EventRealmProxyInterface) object).realmGet$dateB();
        if (realmGet$dateB != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dateBIndex, rowIndex, realmGet$dateB, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dateBIndex, rowIndex, false);
        }
        String realmGet$dateE = ((EventRealmProxyInterface) object).realmGet$dateE();
        if (realmGet$dateE != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dateEIndex, rowIndex, realmGet$dateE, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dateEIndex, rowIndex, false);
        }
        String realmGet$description = ((EventRealmProxyInterface) object).realmGet$description();
        if (realmGet$description != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.descriptionIndex, rowIndex, false);
        }
        Double realmGet$distance = ((EventRealmProxyInterface) object).realmGet$distance();
        if (realmGet$distance != null) {
            Table.nativeSetDouble(tableNativePtr, columnInfo.distanceIndex, rowIndex, realmGet$distance, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.distanceIndex, rowIndex, false);
        }
        Double realmGet$lat = ((EventRealmProxyInterface) object).realmGet$lat();
        if (realmGet$lat != null) {
            Table.nativeSetDouble(tableNativePtr, columnInfo.latIndex, rowIndex, realmGet$lat, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.latIndex, rowIndex, false);
        }
        Double realmGet$lng = ((EventRealmProxyInterface) object).realmGet$lng();
        if (realmGet$lng != null) {
            Table.nativeSetDouble(tableNativePtr, columnInfo.lngIndex, rowIndex, realmGet$lng, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.lngIndex, rowIndex, false);
        }
        String realmGet$tel = ((EventRealmProxyInterface) object).realmGet$tel();
        if (realmGet$tel != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.telIndex, rowIndex, realmGet$tel, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.telIndex, rowIndex, false);
        }
        String realmGet$webSite = ((EventRealmProxyInterface) object).realmGet$webSite();
        if (realmGet$webSite != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.webSiteIndex, rowIndex, realmGet$webSite, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.webSiteIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.typeIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$type(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.likedIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$liked(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.store_idIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$store_id(), false);
        String realmGet$store_name = ((EventRealmProxyInterface) object).realmGet$store_name();
        if (realmGet$store_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.store_nameIndex, rowIndex, realmGet$store_name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.store_nameIndex, rowIndex, false);
        }
        String realmGet$link = ((EventRealmProxyInterface) object).realmGet$link();
        if (realmGet$link != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.linkIndex, rowIndex, realmGet$link, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.linkIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.savedIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$saved(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.featuredIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$featured(), false);

        OsList listImagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.listImagesIndex);
        RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesList = ((EventRealmProxyInterface) object).realmGet$listImages();
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

        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.directoriodelicias.apps.delicias.classes.Event.class);
        long tableNativePtr = table.getNativePtr();
        EventColumnInfo columnInfo = (EventColumnInfo) realm.getSchema().getColumnInfo(com.directoriodelicias.apps.delicias.classes.Event.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.directoriodelicias.apps.delicias.classes.Event object = null;
        while (objects.hasNext()) {
            object = (com.directoriodelicias.apps.delicias.classes.Event) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((EventRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((EventRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((EventRealmProxyInterface) object).realmGet$id());
            }
            cache.put(object, rowIndex);
            Table.nativeSetLong(tableNativePtr, columnInfo.statusIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$status(), false);
            String realmGet$name = ((EventRealmProxyInterface) object).realmGet$name();
            if (realmGet$name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
            }
            String realmGet$address = ((EventRealmProxyInterface) object).realmGet$address();
            if (realmGet$address != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.addressIndex, rowIndex, realmGet$address, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.addressIndex, rowIndex, false);
            }

            com.directoriodelicias.apps.delicias.classes.Images ImagesObj = ((EventRealmProxyInterface) object).realmGet$Images();
            if (ImagesObj != null) {
                Long cacheImages = cache.get(ImagesObj);
                if (cacheImages == null) {
                    cacheImages = ImagesRealmProxy.insertOrUpdate(realm, ImagesObj, cache);
                }
                Table.nativeSetLink(tableNativePtr, columnInfo.ImagesIndex, rowIndex, cacheImages, false);
            } else {
                Table.nativeNullifyLink(tableNativePtr, columnInfo.ImagesIndex, rowIndex);
            }
            String realmGet$imageJson = ((EventRealmProxyInterface) object).realmGet$imageJson();
            if (realmGet$imageJson != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.imageJsonIndex, rowIndex, realmGet$imageJson, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.imageJsonIndex, rowIndex, false);
            }
            String realmGet$dateB = ((EventRealmProxyInterface) object).realmGet$dateB();
            if (realmGet$dateB != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dateBIndex, rowIndex, realmGet$dateB, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dateBIndex, rowIndex, false);
            }
            String realmGet$dateE = ((EventRealmProxyInterface) object).realmGet$dateE();
            if (realmGet$dateE != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dateEIndex, rowIndex, realmGet$dateE, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dateEIndex, rowIndex, false);
            }
            String realmGet$description = ((EventRealmProxyInterface) object).realmGet$description();
            if (realmGet$description != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.descriptionIndex, rowIndex, false);
            }
            Double realmGet$distance = ((EventRealmProxyInterface) object).realmGet$distance();
            if (realmGet$distance != null) {
                Table.nativeSetDouble(tableNativePtr, columnInfo.distanceIndex, rowIndex, realmGet$distance, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.distanceIndex, rowIndex, false);
            }
            Double realmGet$lat = ((EventRealmProxyInterface) object).realmGet$lat();
            if (realmGet$lat != null) {
                Table.nativeSetDouble(tableNativePtr, columnInfo.latIndex, rowIndex, realmGet$lat, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.latIndex, rowIndex, false);
            }
            Double realmGet$lng = ((EventRealmProxyInterface) object).realmGet$lng();
            if (realmGet$lng != null) {
                Table.nativeSetDouble(tableNativePtr, columnInfo.lngIndex, rowIndex, realmGet$lng, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.lngIndex, rowIndex, false);
            }
            String realmGet$tel = ((EventRealmProxyInterface) object).realmGet$tel();
            if (realmGet$tel != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.telIndex, rowIndex, realmGet$tel, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.telIndex, rowIndex, false);
            }
            String realmGet$webSite = ((EventRealmProxyInterface) object).realmGet$webSite();
            if (realmGet$webSite != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.webSiteIndex, rowIndex, realmGet$webSite, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.webSiteIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.typeIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$type(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.likedIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$liked(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.store_idIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$store_id(), false);
            String realmGet$store_name = ((EventRealmProxyInterface) object).realmGet$store_name();
            if (realmGet$store_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.store_nameIndex, rowIndex, realmGet$store_name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.store_nameIndex, rowIndex, false);
            }
            String realmGet$link = ((EventRealmProxyInterface) object).realmGet$link();
            if (realmGet$link != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.linkIndex, rowIndex, realmGet$link, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.linkIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.savedIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$saved(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.featuredIndex, rowIndex, ((EventRealmProxyInterface) object).realmGet$featured(), false);

            OsList listImagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.listImagesIndex);
            RealmList<com.directoriodelicias.apps.delicias.classes.Images> listImagesList = ((EventRealmProxyInterface) object).realmGet$listImages();
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

        }
    }

    public static com.directoriodelicias.apps.delicias.classes.Event createDetachedCopy(com.directoriodelicias.apps.delicias.classes.Event realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.directoriodelicias.apps.delicias.classes.Event unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.directoriodelicias.apps.delicias.classes.Event();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.directoriodelicias.apps.delicias.classes.Event) cachedObject.object;
            }
            unmanagedObject = (com.directoriodelicias.apps.delicias.classes.Event) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        EventRealmProxyInterface unmanagedCopy = (EventRealmProxyInterface) unmanagedObject;
        EventRealmProxyInterface realmSource = (EventRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$status(realmSource.realmGet$status());
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());
        unmanagedCopy.realmSet$name(realmSource.realmGet$name());
        unmanagedCopy.realmSet$address(realmSource.realmGet$address());

        // Deep copy of Images
        unmanagedCopy.realmSet$Images(ImagesRealmProxy.createDetachedCopy(realmSource.realmGet$Images(), currentDepth + 1, maxDepth, cache));
        unmanagedCopy.realmSet$imageJson(realmSource.realmGet$imageJson());
        unmanagedCopy.realmSet$dateB(realmSource.realmGet$dateB());
        unmanagedCopy.realmSet$dateE(realmSource.realmGet$dateE());
        unmanagedCopy.realmSet$description(realmSource.realmGet$description());
        unmanagedCopy.realmSet$distance(realmSource.realmGet$distance());
        unmanagedCopy.realmSet$lat(realmSource.realmGet$lat());
        unmanagedCopy.realmSet$lng(realmSource.realmGet$lng());
        unmanagedCopy.realmSet$tel(realmSource.realmGet$tel());
        unmanagedCopy.realmSet$webSite(realmSource.realmGet$webSite());
        unmanagedCopy.realmSet$type(realmSource.realmGet$type());
        unmanagedCopy.realmSet$liked(realmSource.realmGet$liked());
        unmanagedCopy.realmSet$store_id(realmSource.realmGet$store_id());
        unmanagedCopy.realmSet$store_name(realmSource.realmGet$store_name());
        unmanagedCopy.realmSet$link(realmSource.realmGet$link());
        unmanagedCopy.realmSet$saved(realmSource.realmGet$saved());
        unmanagedCopy.realmSet$featured(realmSource.realmGet$featured());

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

        return unmanagedObject;
    }

    static com.directoriodelicias.apps.delicias.classes.Event update(Realm realm, com.directoriodelicias.apps.delicias.classes.Event realmObject, com.directoriodelicias.apps.delicias.classes.Event newObject, Map<RealmModel, RealmObjectProxy> cache) {
        EventRealmProxyInterface realmObjectTarget = (EventRealmProxyInterface) realmObject;
        EventRealmProxyInterface realmObjectSource = (EventRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$status(realmObjectSource.realmGet$status());
        realmObjectTarget.realmSet$name(realmObjectSource.realmGet$name());
        realmObjectTarget.realmSet$address(realmObjectSource.realmGet$address());
        com.directoriodelicias.apps.delicias.classes.Images ImagesObj = realmObjectSource.realmGet$Images();
        if (ImagesObj == null) {
            realmObjectTarget.realmSet$Images(null);
        } else {
            com.directoriodelicias.apps.delicias.classes.Images cacheImages = (com.directoriodelicias.apps.delicias.classes.Images) cache.get(ImagesObj);
            if (cacheImages != null) {
                realmObjectTarget.realmSet$Images(cacheImages);
            } else {
                realmObjectTarget.realmSet$Images(ImagesRealmProxy.copyOrUpdate(realm, ImagesObj, true, cache));
            }
        }
        realmObjectTarget.realmSet$imageJson(realmObjectSource.realmGet$imageJson());
        realmObjectTarget.realmSet$dateB(realmObjectSource.realmGet$dateB());
        realmObjectTarget.realmSet$dateE(realmObjectSource.realmGet$dateE());
        realmObjectTarget.realmSet$description(realmObjectSource.realmGet$description());
        realmObjectTarget.realmSet$distance(realmObjectSource.realmGet$distance());
        realmObjectTarget.realmSet$lat(realmObjectSource.realmGet$lat());
        realmObjectTarget.realmSet$lng(realmObjectSource.realmGet$lng());
        realmObjectTarget.realmSet$tel(realmObjectSource.realmGet$tel());
        realmObjectTarget.realmSet$webSite(realmObjectSource.realmGet$webSite());
        realmObjectTarget.realmSet$type(realmObjectSource.realmGet$type());
        realmObjectTarget.realmSet$liked(realmObjectSource.realmGet$liked());
        realmObjectTarget.realmSet$store_id(realmObjectSource.realmGet$store_id());
        realmObjectTarget.realmSet$store_name(realmObjectSource.realmGet$store_name());
        realmObjectTarget.realmSet$link(realmObjectSource.realmGet$link());
        realmObjectTarget.realmSet$saved(realmObjectSource.realmGet$saved());
        realmObjectTarget.realmSet$featured(realmObjectSource.realmGet$featured());
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
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Event = proxy[");
        stringBuilder.append("{status:");
        stringBuilder.append(realmGet$status());
        stringBuilder.append("}");
        stringBuilder.append(",");
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
        stringBuilder.append("{Images:");
        stringBuilder.append(realmGet$Images() != null ? "Images" : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{imageJson:");
        stringBuilder.append(realmGet$imageJson() != null ? realmGet$imageJson() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{dateB:");
        stringBuilder.append(realmGet$dateB() != null ? realmGet$dateB() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{dateE:");
        stringBuilder.append(realmGet$dateE() != null ? realmGet$dateE() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{description:");
        stringBuilder.append(realmGet$description() != null ? realmGet$description() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{distance:");
        stringBuilder.append(realmGet$distance() != null ? realmGet$distance() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{lat:");
        stringBuilder.append(realmGet$lat() != null ? realmGet$lat() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{lng:");
        stringBuilder.append(realmGet$lng() != null ? realmGet$lng() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{tel:");
        stringBuilder.append(realmGet$tel() != null ? realmGet$tel() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{webSite:");
        stringBuilder.append(realmGet$webSite() != null ? realmGet$webSite() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{type:");
        stringBuilder.append(realmGet$type());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{liked:");
        stringBuilder.append(realmGet$liked());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{store_id:");
        stringBuilder.append(realmGet$store_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{store_name:");
        stringBuilder.append(realmGet$store_name() != null ? realmGet$store_name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{link:");
        stringBuilder.append(realmGet$link() != null ? realmGet$link() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{saved:");
        stringBuilder.append(realmGet$saved());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{featured:");
        stringBuilder.append(realmGet$featured());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{listImages:");
        stringBuilder.append("RealmList<Images>[").append(realmGet$listImages().size()).append("]");
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
        EventRealmProxy aEvent = (EventRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aEvent.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aEvent.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aEvent.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
