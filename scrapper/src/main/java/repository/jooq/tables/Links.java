/*
 * This file is generated by jOOQ.
 */
package repository.jooq.tables;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function5;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row5;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import repository.jooq.DefaultSchema;
import repository.jooq.Keys;
import repository.jooq.tables.records.LinksRecord;


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
public class Links extends TableImpl<LinksRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>LINKS</code>
     */
    public static final Links LINKS = new Links();

    /**
     * The class holding records for this type
     */
    @Override
    @NotNull
    public Class<LinksRecord> getRecordType() {
        return LinksRecord.class;
    }

    /**
     * The column <code>LINKS.ID</code>.
     */
    public final TableField<LinksRecord, Long> ID = createField(DSL.name("ID"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>LINKS.URL</code>.
     */
    public final TableField<LinksRecord, String> URL = createField(DSL.name("URL"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>LINKS.LAST_UPDATE</code>.
     */
    public final TableField<LinksRecord, LocalDateTime> LAST_UPDATE = createField(DSL.name("LAST_UPDATE"), SQLDataType.LOCALDATETIME(6), this, "");

    /**
     * The column <code>LINKS.LAST_CHECK</code>.
     */
    public final TableField<LinksRecord, LocalDateTime> LAST_CHECK = createField(DSL.name("LAST_CHECK"), SQLDataType.LOCALDATETIME(6), this, "");

    /**
     * The column <code>LINKS.SITE_ID</code>.
     */
    public final TableField<LinksRecord, Long> SITE_ID = createField(DSL.name("SITE_ID"), SQLDataType.BIGINT.nullable(false), this, "");

    private Links(Name alias, Table<LinksRecord> aliased) {
        this(alias, aliased, null);
    }

    private Links(Name alias, Table<LinksRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>LINKS</code> table reference
     */
    public Links(String alias) {
        this(DSL.name(alias), LINKS);
    }

    /**
     * Create an aliased <code>LINKS</code> table reference
     */
    public Links(Name alias) {
        this(alias, LINKS);
    }

    /**
     * Create a <code>LINKS</code> table reference
     */
    public Links() {
        this(DSL.name("LINKS"), null);
    }

    public <O extends Record> Links(Table<O> child, ForeignKey<O, LinksRecord> key) {
        super(child, key, LINKS);
    }

    @Override
    @Nullable
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    @NotNull
    public Identity<LinksRecord, Long> getIdentity() {
        return (Identity<LinksRecord, Long>) super.getIdentity();
    }

    @Override
    @NotNull
    public UniqueKey<LinksRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_45;
    }

    @Override
    @NotNull
    public List<UniqueKey<LinksRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.CONSTRAINT_451);
    }

    @Override
    @NotNull
    public List<ForeignKey<LinksRecord, ?>> getReferences() {
        return Arrays.asList(Keys.FK);
    }

    private transient Sites _sites;

    /**
     * Get the implicit join path to the <code>PUBLIC.SITES</code> table.
     */
    public Sites sites() {
        if (_sites == null)
            _sites = new Sites(this, Keys.FK);

        return _sites;
    }

    @Override
    @NotNull
    public Links as(String alias) {
        return new Links(DSL.name(alias), this);
    }

    @Override
    @NotNull
    public Links as(Name alias) {
        return new Links(alias, this);
    }

    @Override
    @NotNull
    public Links as(Table<?> alias) {
        return new Links(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public Links rename(String name) {
        return new Links(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public Links rename(Name name) {
        return new Links(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public Links rename(Table<?> name) {
        return new Links(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row5<Long, String, LocalDateTime, LocalDateTime, Long> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function5<? super Long, ? super String, ? super LocalDateTime, ? super LocalDateTime, ? super Long, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function5<? super Long, ? super String, ? super LocalDateTime, ? super LocalDateTime, ? super Long, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
