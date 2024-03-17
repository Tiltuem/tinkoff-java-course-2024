/*
 * This file is generated by jOOQ.
 */
package repository.jooq.tables.records;


import jakarta.validation.constraints.Size;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;

import repository.jooq.tables.Links;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class LinksRecord extends UpdatableRecordImpl<LinksRecord> implements Record5<Long, String, LocalDateTime, LocalDateTime, Long> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>LINKS.ID</code>.
     */
    public void setId(@Nullable Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>LINKS.ID</code>.
     */
    @Nullable
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>LINKS.URL</code>.
     */
    public void setUrl(@NotNull String value) {
        set(1, value);
    }

    /**
     * Getter for <code>LINKS.URL</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 255)
    @NotNull
    public String getUrl() {
        return (String) get(1);
    }

    /**
     * Setter for <code>LINKS.LAST_UPDATE</code>.
     */
    public void setLastUpdate(@Nullable LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>LINKS.LAST_UPDATE</code>.
     */
    @Nullable
    public LocalDateTime getLastUpdate() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>LINKS.LAST_CHECK</code>.
     */
    public void setLastCheck(@Nullable LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>LINKS.LAST_CHECK</code>.
     */
    @Nullable
    public LocalDateTime getLastCheck() {
        return (LocalDateTime) get(3);
    }

    /**
     * Setter for <code>LINKS.SITE_ID</code>.
     */
    public void setSiteId(@NotNull Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>LINKS.SITE_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getSiteId() {
        return (Long) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row5<Long, String, LocalDateTime, LocalDateTime, Long> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row5<Long, String, LocalDateTime, LocalDateTime, Long> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Long> field1() {
        return Links.LINKS.ID;
    }

    @Override
    @NotNull
    public Field<String> field2() {
        return Links.LINKS.URL;
    }

    @Override
    @NotNull
    public Field<LocalDateTime> field3() {
        return Links.LINKS.LAST_UPDATE;
    }

    @Override
    @NotNull
    public Field<LocalDateTime> field4() {
        return Links.LINKS.LAST_CHECK;
    }

    @Override
    @NotNull
    public Field<Long> field5() {
        return Links.LINKS.SITE_ID;
    }

    @Override
    @Nullable
    public Long component1() {
        return getId();
    }

    @Override
    @NotNull
    public String component2() {
        return getUrl();
    }

    @Override
    @Nullable
    public LocalDateTime component3() {
        return getLastUpdate();
    }

    @Override
    @Nullable
    public LocalDateTime component4() {
        return getLastCheck();
    }

    @Override
    @NotNull
    public Long component5() {
        return getSiteId();
    }

    @Override
    @Nullable
    public Long value1() {
        return getId();
    }

    @Override
    @NotNull
    public String value2() {
        return getUrl();
    }

    @Override
    @Nullable
    public LocalDateTime value3() {
        return getLastUpdate();
    }

    @Override
    @Nullable
    public LocalDateTime value4() {
        return getLastCheck();
    }

    @Override
    @NotNull
    public Long value5() {
        return getSiteId();
    }

    @Override
    @NotNull
    public LinksRecord value1(@Nullable Long value) {
        setId(value);
        return this;
    }

    @Override
    @NotNull
    public LinksRecord value2(@NotNull String value) {
        setUrl(value);
        return this;
    }

    @Override
    @NotNull
    public LinksRecord value3(@Nullable LocalDateTime value) {
        setLastUpdate(value);
        return this;
    }

    @Override
    @NotNull
    public LinksRecord value4(@Nullable LocalDateTime value) {
        setLastCheck(value);
        return this;
    }

    @Override
    @NotNull
    public LinksRecord value5(@NotNull Long value) {
        setSiteId(value);
        return this;
    }

    @Override
    @NotNull
    public LinksRecord values(@Nullable Long value1, @NotNull String value2, @Nullable LocalDateTime value3, @Nullable LocalDateTime value4, @NotNull Long value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LinksRecord
     */
    public LinksRecord() {
        super(Links.LINKS);
    }

    /**
     * Create a detached, initialised LinksRecord
     */
    @ConstructorProperties({ "id", "url", "lastUpdate", "lastCheck", "siteId" })
    public LinksRecord(@Nullable Long id, @NotNull String url, @Nullable LocalDateTime lastUpdate, @Nullable LocalDateTime lastCheck, @NotNull Long siteId) {
        super(Links.LINKS);

        setId(id);
        setUrl(url);
        setLastUpdate(lastUpdate);
        setLastCheck(lastCheck);
        setSiteId(siteId);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised LinksRecord
     */
    public LinksRecord(repository.jooq.tables.pojos.Links value) {
        super(Links.LINKS);

        if (value != null) {
            setId(value.getId());
            setUrl(value.getUrl());
            setLastUpdate(value.getLastUpdate());
            setLastCheck(value.getLastCheck());
            setSiteId(value.getSiteId());
            resetChangedOnNotNull();
        }
    }
}
