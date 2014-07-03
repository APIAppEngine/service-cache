package apiserver.services.cache.services;

/*******************************************************************************
 Copyright (c) 2013 Mike Nimer.

 This file is part of ApiServer Project.

 The ApiServer Project is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The ApiServer Project is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with the ApiServer Project.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

import apiserver.core.model.IDocument;
import apiserver.services.cache.DocumentJob;
import apiserver.services.cache.gateway.jobs.GetDocumentJob;
import apiserver.services.cache.providers.IDocumentCacheProvider;
import apiserver.exceptions.MessageConfigException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import java.io.IOException;

/**
 * User: mnimer
 * Date: 9/27/12
 */
// todo? investigate cache regions, maybe put all of these in a document 'region' to separate the cache from connection and encryption cache
public class DocumentService
{

    @Autowired
    private IDocumentCacheProvider cacheProvider;


    public Message<?> getFromCache(Message<?> message) throws MessageConfigException, IOException
    {
        Object payload = message.getPayload();
        if( !(payload instanceof GetDocumentJob) || ((GetDocumentJob)payload).getDocumentId() == null ){ return message; }

        GetDocumentJob p = (GetDocumentJob) payload;
        IDocument document = cacheProvider.get(p.getDocumentId());
        p.setDocument(document);
        return message;
    }


    public Message<?> addToCache(Message<?> message)
    {
        if( !(message.getPayload() instanceof DocumentJob) ){ return message; }

        DocumentJob payload = (DocumentJob) message.getPayload();

        if( payload.getDocument() != null )
        {
            IDocument document = cacheProvider.add(payload.getDocument());
            payload.setDocument(document);
        }


        return message;
    }



    public Message<?> deleteFromCache(Message<?> message)
    {
        if( !(message.getPayload() instanceof DocumentJob) ){ return message; }

        return message;
    }

}
