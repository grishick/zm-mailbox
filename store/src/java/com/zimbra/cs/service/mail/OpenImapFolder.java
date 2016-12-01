package com.zimbra.cs.service.mail;

import java.util.Map;

import com.google.common.base.Joiner;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;
import com.zimbra.cs.imap.ImapMessage;
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.cs.mailbox.OperationContext;
import com.zimbra.cs.service.account.AccountDocumentHandler;
import com.zimbra.cs.service.util.ItemId;
import com.zimbra.soap.JaxbUtil;
import com.zimbra.soap.ZimbraSoapContext;
import com.zimbra.soap.admin.message.ImapMessageInfo;
import com.zimbra.soap.admin.message.OpenImapFolderRequest;
import com.zimbra.soap.admin.message.OpenImapFolderResponse;

public class OpenImapFolder extends AccountDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context)
            throws ServiceException {
        ZimbraSoapContext zsc = getZimbraSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);
        OpenImapFolderRequest req = JaxbUtil.elementToJaxb(request);
        OpenImapFolderResponse resp = new OpenImapFolderResponse();
        ItemId folderId = new ItemId(req.getFolderId(), zsc);
        for (ImapMessage msg: mbox.openImapFolder(octxt, folderId.getId())) {
            int id = msg.getMsgId();
            int imapId = msg.getImapId();
            String type = msg.getType().toString();
            int flags = msg.getFlags();
            String tags = msg.getTags() == null ? null : Joiner.on(",").join(msg.getTags());
            ImapMessageInfo info = new ImapMessageInfo(id, imapId, type, flags, tags);
            resp.addImapMessageInfo(info);
        }
        return JaxbUtil.jaxbToElement(resp);
    }

}
