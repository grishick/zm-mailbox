/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012, 2013, 2014 Zimbra, Inc.
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation,
 * version 2 of the License.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 * ***** END LICENSE BLOCK *****
 */

package com.zimbra.soap.account.message;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zimbra.common.soap.AccountConstants;
import com.zimbra.common.soap.MailConstants;
import com.zimbra.soap.account.type.ContactInfo;
import com.zimbra.soap.type.Id;
import com.zimbra.soap.type.ZmBoolean;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=AccountConstants.E_SYNC_GAL_RESPONSE)
@XmlType(propOrder = {})
public class SyncGalResponse {

    /**
     * @zm-api-field-tag more-flag
     * @zm-api-field-description Flags whether there are more results
     */
    @XmlAttribute(name=MailConstants.A_QUERY_MORE /* more */, required=false)
    private ZmBoolean more;

    /**
     * @zm-api-field-tag new-token
     * @zm-api-field-description New synchronization token
     */
    @XmlAttribute(name=MailConstants.A_TOKEN /* token */, required=false)
    private String token;
    
    /**
     * @zm-api-field-tag galDefinitionLastModified
     * @zm-api-field-description galDefinitionLastModified is the time at which the GAL definition is last modified.
     * This is returned if the sync does not happen using GAL sync account.
     */
    @XmlAttribute(name=MailConstants.A_GAL_DEFINITION_LAST_MODIFIED /* galDefinitionLastModified */, required=false)
    private String galDefinitionLastModified;
    
    /**
     * @zm-api-field-tag throttled-flag
     * @zm-api-field-description True if the SyncGal request is throttled
     */
    @XmlAttribute(name=MailConstants.A_GALSYNC_THROTTLED /* throttled */, required=false)
    private ZmBoolean throttled;
    
    /**
     * @zm-api-field-tag fullSyncRecommended-flag
     * @zm-api-field-description True if the fullSync is recommended
     */
    @XmlAttribute(name=MailConstants.A_GALSYNC_FULLSYNC_RECOMMENDED /* fullSyncRecommended */, required=false)
    private ZmBoolean fullSyncRecommended;
    
    /**
     * @zm-api-field-description Details of contact.  For element names <b>&lt;deleted</b> - gives details of deleted
     * entries.
     */
    @XmlElements({
        @XmlElement(name=MailConstants.E_CONTACT /* cn */, type=ContactInfo.class),
        @XmlElement(name=MailConstants.E_DELETED /* deleted */, type=Id.class)
    })
    private List<Object> hits = Lists.newArrayList();

    /**
     * @zm-api-field-tag new LdapOffset
     * @zm-api-field-description New ldapOffset from where paged LDAP sync should resume.
     */
    @XmlAttribute(name=MailConstants.A_LDAP_OFFSET /* token */, required=false)
    private String ldapOffset;

    public SyncGalResponse() {
    }

    public void setMore(Boolean more) { this.more = ZmBoolean.fromBool(more); }
    public void setToken(String token) { this.token = token; }
    public void setLdapOffset(String ldapOffset) {this.ldapOffset = ldapOffset;}
    public void setGalDefinitionLastModified(String timestamp) { this.galDefinitionLastModified = timestamp; }
    public void setThrottled(Boolean throttled) { this.throttled = ZmBoolean.fromBool(throttled); }
    public void setFullSyncRecommended(Boolean value) { this.fullSyncRecommended = ZmBoolean.fromBool(value); }
    public void setHits(Iterable <Object> hits) {
        this.hits.clear();
        if (hits != null) {
            Iterables.addAll(this.hits,hits);
        }
    }

    public void addHit(Object hit) {
        this.hits.add(hit);
    }

    public Boolean getMore() { return ZmBoolean.toBool(more); }
    public Boolean getThrottled() { return ZmBoolean.toBool(throttled); }
    public Boolean getFullSyncRecommended() { return ZmBoolean.toBool(fullSyncRecommended); }
    public String getToken() { return token; }
    public String getLdapOffset() {return ldapOffset;}
    public String getGalDefinitionLastModified() { return galDefinitionLastModified; }
    public List<Object> getHits() {
        return Collections.unmodifiableList(hits);
    }

    public Objects.ToStringHelper addToStringInfo(
                Objects.ToStringHelper helper) {
        return helper
            .add("more", more)
            .add("token", token)
            .add("ldapOffset", ldapOffset)
            .add("galDefinitionLastModified", galDefinitionLastModified)
            .add("throttled", throttled)
            .add("fullSyncRecommended", fullSyncRecommended)
            .add("hits", hits);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this))
                .toString();
    }
}
