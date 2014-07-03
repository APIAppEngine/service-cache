package apiserver.services.cache.services;

import apiserver.core.connectors.coldfusion.services.CollectionResult;
import apiserver.core.model.IDocument;
import apiserver.services.cache.model.Document;
import apiserver.services.cache.providers.IDocumentCacheProvider;
import apiserver.workers.coldfusion.model.FileByteWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Wrapper around the document service that will take one or more files -> cache them and replace them with a URL to the file in cache
 * Created by mnimer on 7/3/14.
 */
public class CDNService
{
    @Autowired
    private IDocumentCacheProvider cacheProvider;


    public Message<?> cacheFilesInCDN(Message<?> message) throws IOException
    {
        if (message.getPayload() instanceof CollectionResult) {
            CollectionResult payload = (CollectionResult) message.getPayload();
            Collection<String> resultUrls = new ArrayList<String>();
            Object[] fileArray = payload.getResult().toArray();

            for (int i = 0; i < fileArray.length; i++) {
                FileByteWrapper wrapper = (FileByteWrapper) fileArray[i];
                String id = addFileToCache(wrapper);
                String relativeUrl = "/cache/documents/" +id;
                resultUrls.add(relativeUrl);
            }

            payload.setResult(resultUrls);

            //Message msg = MessageBuilder.withPayload(resultUrls).copyHeaders(message.getHeaders()).build();
            //return msg;
        }

        return message;
    }


    private String addFileToCache(FileByteWrapper file) throws IOException
    {
        Document doc = new Document(file);
        IDocument document = cacheProvider.add(doc);

        return document.getId();
    }
}
