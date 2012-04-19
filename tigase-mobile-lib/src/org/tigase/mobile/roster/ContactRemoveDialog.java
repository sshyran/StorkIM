package org.tigase.mobile.roster;

import org.tigase.mobile.MessengerApplication;
import org.tigase.mobile.RosterDisplayTools;
import org.tigase.mobile.db.RosterTableMetaData;
import org.tigase.mobile.db.providers.RosterProvider;

import tigase.jaxmpp.core.client.BareJID;
import tigase.jaxmpp.core.client.JID;
import tigase.jaxmpp.core.client.xmpp.modules.roster.RosterItem;
import tigase.jaxmpp.j2se.Jaxmpp;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ContactRemoveDialog extends DialogFragment {

	public static ContactRemoveDialog newInstance(final long id) {
		ContactRemoveDialog frag = new ContactRemoveDialog();
		Bundle args = new Bundle();
		args.putLong("itemId", id);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final long id = getArguments().getLong("itemId");

		final Cursor cursor = getActivity().getContentResolver().query(Uri.parse(RosterProvider.CONTENT_URI + "/" + id), null,
				null, null, null);
		JID jid = null;
		BareJID account = null;
		try {
			cursor.moveToNext();
			jid = JID.jidInstance(cursor.getString(cursor.getColumnIndex(RosterTableMetaData.FIELD_JID)));
			account = BareJID.bareJIDInstance(cursor.getString(cursor.getColumnIndex(RosterTableMetaData.FIELD_ACCOUNT)));
		} finally {
			cursor.close();
		}
		final Jaxmpp jaxmpp = ((MessengerApplication) getActivity().getApplicationContext()).getMultiJaxmpp().get(account);
		final RosterItem rosterItem = jaxmpp.getRoster().get(jid.getBareJid());

		String name = (new RosterDisplayTools(getActivity().getApplicationContext())).getDisplayName(rosterItem);

		((MessengerApplication) getActivity().getApplication()).getTracker().trackPageView("/contactRemoveDialog");

		return new AlertDialog.Builder(getActivity()).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Delete").setMessage(
				"Do you want to remove " + name + " (" + rosterItem.getJid() + ")").setPositiveButton(android.R.string.yes,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						final Runnable r = new Runnable() {
							@Override
							public void run() {

								try {
									jaxmpp.getRoster().remove(rosterItem.getJid());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						};
						(new Thread(r)).start();

					}
				}).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				// ((ContactRemoveDialog)getActivity()).doNegativeClick();
			}
		}).create();
	}

}