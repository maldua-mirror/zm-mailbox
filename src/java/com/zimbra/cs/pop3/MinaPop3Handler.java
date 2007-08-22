/*
 * ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 ("License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.zimbra.com/license
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and limitations
 * under the License.
 *
 * The Original Code is: Zimbra Collaboration Suite Server.
 *
 * The Initial Developer of the Original Code is Zimbra, Inc.
 * Portions created by Zimbra are Copyright (C) 2004, 2005, 2006 Zimbra, Inc.
 * All Rights Reserved.
 *
 * Contributor(s):
 *
 * ***** END LICENSE BLOCK *****
 */

package com.zimbra.cs.pop3;

import com.zimbra.cs.mina.MinaHandler;
import com.zimbra.cs.mina.MinaRequest;
import com.zimbra.cs.mina.MinaIoSessionOutputStream;
import com.zimbra.cs.mina.MinaTextLineRequest;
import com.zimbra.common.util.ZimbraLog;
import org.apache.mina.common.IoSession;
import org.apache.mina.common.IdleStatus;
import org.apache.mina.filter.SSLFilter;

import java.io.IOException;
import java.net.Socket;
import java.net.InetSocketAddress;

public class MinaPop3Handler extends Pop3Handler implements MinaHandler {
    private IoSession mSession;

    public MinaPop3Handler(MinaPop3Server server, IoSession session) {
        super(server);
        this.mSession = session;
    }

    public void connectionOpened() throws IOException {
        mOutputStream = new MinaIoSessionOutputStream(mSession);
        mSession.setIdleTime(IdleStatus.BOTH_IDLE, mConfig.getMaxIdleSeconds());
        setupConnection(((InetSocketAddress) mSession.getRemoteAddress()).getAddress());
    }

    public void connectionClosed() throws IOException {
        dropConnection();
    }

    public void connectionIdle() {
        ZimbraLog.pop.debug("idle connection");
        dropConnection();
    }
    
    public void requestReceived(MinaRequest req) throws IOException {
        try {
            processCommand(((MinaTextLineRequest) req).getLine());
        } finally {
            if (dropConnection) dropConnection();
        }
    }

    @Override
    protected void startTLS() throws IOException {
        SSLFilter filter = new SSLFilter(MinaPop3Server.getSSLContext());
        mSession.getFilterChain().addFirst("ssl", filter);
        mSession.setAttribute(SSLFilter.DISABLE_ENCRYPTION_ONCE, true);
        sendOK("Begin TLS negotiation");
    }
    
    @Override
    protected void dropConnection() {
        if (!mSession.isClosing()) {
            try {
                mOutputStream.close();
            } catch (IOException e) {
                // Should never happen...
            }
            mSession.close();
        }
    }

    @Override
    protected boolean setupConnection(Socket connection) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected boolean processCommand() {
        throw new UnsupportedOperationException();
    }
}
