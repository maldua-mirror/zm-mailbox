/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012, 2013, 2014, 2016 Synacor, Inc.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation,
 * version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 * ***** END LICENSE BLOCK *****
 */

package com.zimbra.soap.admin.message;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zimbra.common.soap.AccountConstants;
import com.zimbra.common.soap.AdminConstants;
import com.zimbra.soap.admin.type.ZimletStatusCos;
import com.zimbra.soap.admin.type.ZimletStatusParent;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=AdminConstants.E_GET_ZIMLET_STATUS_RESPONSE)
@XmlType(propOrder = {"zimlets", "coses"})
public class GetZimletStatusResponse {

    /**
     * @zm-api-field-description Information about status of zimlets
     */
    @XmlElement(name=AccountConstants.E_ZIMLETS, required=true)
    private final ZimletStatusParent zimlets;

    /**
     * @zm-api-field-description Class Of Service (COS) Information
     */
    @XmlElement(name=AdminConstants.E_COS, required=false)
    private List<ZimletStatusCos> coses = Lists.newArrayList();

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private GetZimletStatusResponse() {
        this((ZimletStatusParent) null);
    }

    public GetZimletStatusResponse(ZimletStatusParent zimlets) {
        this.zimlets = zimlets;
    }

    public void setCoses(Iterable <ZimletStatusCos> coses) {
        this.coses.clear();
        if (coses != null) {
            Iterables.addAll(this.coses,coses);
        }
    }

    public GetZimletStatusResponse addCos(ZimletStatusCos cos) {
        this.coses.add(cos);
        return this;
    }

    public ZimletStatusParent getZimlets() { return zimlets; }
    public List<ZimletStatusCos> getCoses() {
        return Collections.unmodifiableList(coses);
    }
}
