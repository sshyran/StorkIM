/*
 * Stork
 * Copyright (C) 2019 Tigase, Inc. (office@tigase.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 */

package org.tigase.messenger.phone.pro.conversations;

import android.content.Intent;
import android.view.MenuItem;
import org.tigase.messenger.AbstractServiceActivity;
import org.tigase.messenger.phone.pro.MainActivity;
import tigase.jaxmpp.core.client.BareJID;
import tigase.jaxmpp.core.client.JID;

public class AbstractConversationActivity
		extends AbstractServiceActivity {

	public static final String TEXT = "TEXT";

	public static final int FILE_UPLOAD_REQUEST_CODE = 109;

	private BareJID account;
	private JID jid;

	public BareJID getAccount() {
		return account;
	}

	protected void setAccount(BareJID account) {
		this.account = account;
	}

	public JID getJid() {
		return jid;
	}

	protected void setJid(JID jid) {
		this.jid = jid;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			startActivity(new Intent(this, MainActivity.class));
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onXMPPServiceConnected() {

	}

	@Override
	protected void onXMPPServiceDisconnected() {

	}

}
