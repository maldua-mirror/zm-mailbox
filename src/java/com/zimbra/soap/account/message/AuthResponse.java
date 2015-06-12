/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012, 2013, 2014 Zimbra, Inc.
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.google.common.collect.Multimap;
import com.zimbra.common.soap.AccountConstants;
import com.zimbra.common.soap.HeaderConstants;
import com.zimbra.soap.account.type.Attr;
import com.zimbra.soap.account.type.Pref;
import com.zimbra.soap.account.type.Session;
import com.zimbra.soap.json.jackson.annotate.ZimbraJsonAttribute;
import com.zimbra.soap.json.jackson.annotate.ZimbraKeyValuePairs;
import com.zimbra.soap.type.ZmBoolean;


/*
<AuthResponse">
   <authToken>...</authToken>
   <lifetime>{lifetime-in-milliseconds}</lifetime>
   <session .../>
   <refer>{mail-host}</refer>
   [<prefs><pref name="{name}" modified="{modified-time}">{value}</pref>...</prefs>]
   [<attrs><attr name="{name}">{value}</a>...</attrs>]
   [<skin>{skin-name}</skin>]
   [<csrfToken>{csrf-token}</csrfToken>]
 </AuthResponse>
/**
 * @zm-api-response-description Response to account authentication request.
 */
@XmlRootElement(name=AccountConstants.E_AUTH_RESPONSE)
@XmlType(propOrder = {})
public class AuthResponse {

    /**
     * @zm-api-field-description The authorization token
     */
    @XmlElement(name=AccountConstants.E_AUTH_TOKEN /* authToken */, required=true)
    private String authToken;
    /**
     * @zm-api-field-description Life time for the authorization - given in milliseconds
     */
    @ZimbraJsonAttribute
    @XmlElement(name=AccountConstants.E_LIFETIME /* lifetime */, required=true)
    private long lifetime;

    /**
     * @zm-api-field-description trust lifetime, if a trusted token is issued
     */
    @ZimbraJsonAttribute
    @XmlElement(name=AccountConstants.E_TRUST_LIFETIME /* trustLifetime */, required=false)
    private long trustLifetime;

    /**
     * @zm-api-field-description Session information
     */
    @XmlElement(name=HeaderConstants.E_SESSION /* session */, required=false)
    private Session session;
    /**
     * @zm-api-field-description host additional SOAP requests should be directed to.
     * Always returned, might be same as original host request was sent to.
     */
    @ZimbraJsonAttribute
    @XmlElement(name=AccountConstants.E_REFERRAL /* refer */, required=false)
    private String refer;
    /**
     * @zm-api-field-description if requestedSkin specified, the name of the skin to use
     * Always returned, might be same as original host request was sent to.
     */
    @XmlElement(name=AccountConstants.E_SKIN /* skin */, required=false)
    private String skin;

    /**
     * @zm-api-field-description if client is CSRF token enabled , the CSRF token
     * Returned only when client says it is CSRF enabled .
     */
    @XmlElement(name=HeaderConstants.E_CSRFTOKEN /* CSRF token*/, required=false)
    private String csrfToken;

    /**
     * @zm-api-field-description random secure device ID generated for the requesting device
     */
    @XmlElement(name=AccountConstants.E_DEVICE_ID, required=false)
    private String deviceId;

    /**
     * @zm-api-field-description trusted device token
     */
    @XmlElement(name=AccountConstants.E_TRUSTED_TOKEN /* trustedToken */, required=false)
    private String trustedToken;

    /**
     * @zm-api-field-description Requested preference settings.
     */
    @ZimbraKeyValuePairs
    @XmlElementWrapper(name=AccountConstants.E_PREFS /* prefs */)
    @XmlElement(name=AccountConstants.E_PREF /* pref */)
    private List<Pref> prefs = new ArrayList<Pref>();

    /**
     * @zm-api-field-description Requested attribute settings.  Only attributes that are allowed to be returned by
     * GetInfo will be returned by this call
     */
    @ZimbraKeyValuePairs
    @XmlElementWrapper(name=AccountConstants.E_ATTRS /* attrs */)
    @XmlElement(name=AccountConstants.E_ATTR /* attr */)
    private List<Attr> attrs = new ArrayList<Attr>();

    @XmlElement(name=AccountConstants.E_TWO_FACTOR_AUTH_REQUIRED, required =false)
    private ZmBoolean twoFactorAuthRequired;

    public AuthResponse() {
    }

    public AuthResponse(String authToken, long lifetime) {
        setAuthToken(authToken);
        setLifetime(lifetime);
    }

    public String getAuthToken() { return authToken; }
    public AuthResponse setAuthToken(String authToken) { this.authToken = authToken; return this; }

    public long getLifetime() { return lifetime; }
    public AuthResponse setLifetime(long lifetime) { this.lifetime = lifetime; return this; }

    public Session getSession() { return session; }
    public AuthResponse setSession(Session session) { this.session = session; return this; }

    public String getRefer() { return refer; }
    public AuthResponse setRefer(String refer) { this.refer = refer; return this; }

    public String getSkin() { return skin; }
    public AuthResponse setSkin(String skin) { this.skin = skin; return this; }

    public List<Pref> getPrefs() { return Collections.unmodifiableList(prefs); }
    public AuthResponse setPrefs(Collection<Pref> prefs) {
        this.prefs.clear();
        if (prefs != null) {
            this.prefs.addAll(prefs);
        }
        return this;
    }

    public List<Attr> getAttrs() { return Collections.unmodifiableList(attrs); }
    public AuthResponse setAttrs(Collection<Attr> attrs) {
        this.attrs.clear();
        if (attrs != null) {
            this.attrs.addAll(attrs);
        }
        return this;
    }

    public Multimap<String, String> getAttrsMultimap() {
        return Attr.toMultimap(attrs);
    }

    public Multimap<String, String> getPrefsMultimap() {
        return Pref.toMultimap(prefs);
    }


    /**
     * @return the csrfToken
     */
    public String getCsrfToken() {
        return csrfToken;
    }


    /**
     * @param csrfToken the csrfToken to set
     */
    public void setCsrfToken(String csrfToken) {
        this.csrfToken = csrfToken;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setTrustedToken(String trustedToken) {
        this.trustedToken = trustedToken;
    }

    public String getTrustedToken() {
        return trustedToken;
    }

    public Long getTrustLifetime() { return trustLifetime; }
    public AuthResponse setTrustLifetime(Long trustLifetime) { this.trustLifetime = trustLifetime; return this; }

    public ZmBoolean getTwoFactorAuthRequired() { return twoFactorAuthRequired; }
    public AuthResponse setTwoFactorAuthRequired(boolean bool) { this.twoFactorAuthRequired = ZmBoolean.fromBool(bool); return this; }
}
