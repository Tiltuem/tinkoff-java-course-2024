/*
 * This file is generated by jOOQ.
 */

package edu.java.repository.jooq.tables.records;

import edu.java.repository.jooq.tables.UserLinks;
import java.beans.ConstructorProperties;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


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
public class UserLinksRecord extends UpdatableRecordImpl<UserLinksRecord> implements Record2<Long, Long> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>USER_LINKS.USER_ID</code>.
     */
    public void setUserId(@NotNull Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>USER_LINKS.USER_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getUserId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>USER_LINKS.LINK_ID</code>.
     */
    public void setLinkId(@NotNull Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>USER_LINKS.LINK_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getLinkId() {
        return (Long) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record2<Long, Long> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row2<Long, Long> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Long> field1() {
        return UserLinks.USER_LINKS.USER_ID;
    }

    @Override
    @NotNull
    public Field<Long> field2() {
        return UserLinks.USER_LINKS.LINK_ID;
    }

    @Override
    @NotNull
    public Long component1() {
        return getUserId();
    }

    @Override
    @NotNull
    public Long component2() {
        return getLinkId();
    }

    @Override
    @NotNull
    public Long value1() {
        return getUserId();
    }

    @Override
    @NotNull
    public Long value2() {
        return getLinkId();
    }

    @Override
    @NotNull
    public UserLinksRecord value1(@NotNull Long value) {
        setUserId(value);
        return this;
    }

    @Override
    @NotNull
    public UserLinksRecord value2(@NotNull Long value) {
        setLinkId(value);
        return this;
    }

    @Override
    @NotNull
    public UserLinksRecord values(@NotNull Long value1, @NotNull Long value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserLinksRecord
     */
    public UserLinksRecord() {
        super(UserLinks.USER_LINKS);
    }

    /**
     * Create a detached, initialised UserLinksRecord
     */
    @ConstructorProperties({ "userId", "linkId" })
    public UserLinksRecord(@NotNull Long userId, @NotNull Long linkId) {
        super(UserLinks.USER_LINKS);

        setUserId(userId);
        setLinkId(linkId);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised UserLinksRecord
     */
    public UserLinksRecord(edu.java.repository.jooq.tables.pojos.UserLinks value) {
        super(UserLinks.USER_LINKS);

        if (value != null) {
            setUserId(value.getUserId());
            setLinkId(value.getLinkId());
            resetChangedOnNotNull();
        }
    }
}
