/*
 * This file is generated by jOOQ.
 */
package repository.jooq.tables;


import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function2;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row2;
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
import repository.jooq.tables.records.SitesRecord;


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
public class Sites extends TableImpl<SitesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>SITES</code>
     */
    public static final Sites SITES = new Sites();

    /**
     * The class holding records for this type
     */
    @Override
    @NotNull
    public Class<SitesRecord> getRecordType() {
        return SitesRecord.class;
    }

    /**
     * The column <code>SITES.ID</code>.
     */
    public final TableField<SitesRecord, Long> ID = createField(DSL.name("ID"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>SITES.SITE_NAME</code>.
     */
    public final TableField<SitesRecord, String> SITE_NAME = createField(DSL.name("SITE_NAME"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    private Sites(Name alias, Table<SitesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Sites(Name alias, Table<SitesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>SITES</code> table reference
     */
    public Sites(String alias) {
        this(DSL.name(alias), SITES);
    }

    /**
     * Create an aliased <code>SITES</code> table reference
     */
    public Sites(Name alias) {
        this(alias, SITES);
    }

    /**
     * Create a <code>SITES</code> table reference
     */
    public Sites() {
        this(DSL.name("SITES"), null);
    }

    public <O extends Record> Sites(Table<O> child, ForeignKey<O, SitesRecord> key) {
        super(child, key, SITES);
    }

    @Override
    @Nullable
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    @NotNull
    public Identity<SitesRecord, Long> getIdentity() {
        return (Identity<SitesRecord, Long>) super.getIdentity();
    }

    @Override
    @NotNull
    public UniqueKey<SitesRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_4B;
    }

    @Override
    @NotNull
    public List<UniqueKey<SitesRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.CONSTRAINT_4B4);
    }

    @Override
    @NotNull
    public Sites as(String alias) {
        return new Sites(DSL.name(alias), this);
    }

    @Override
    @NotNull
    public Sites as(Name alias) {
        return new Sites(alias, this);
    }

    @Override
    @NotNull
    public Sites as(Table<?> alias) {
        return new Sites(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public Sites rename(String name) {
        return new Sites(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public Sites rename(Name name) {
        return new Sites(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public Sites rename(Table<?> name) {
        return new Sites(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row2<Long, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function2<? super Long, ? super String, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function2<? super Long, ? super String, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}