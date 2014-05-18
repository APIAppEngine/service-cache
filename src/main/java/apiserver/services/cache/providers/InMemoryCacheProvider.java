package apiserver.services.cache.providers;

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

import apiserver.MimeType;
import apiserver.core.model.IDocument;
import apiserver.services.cache.DocumentJob;
import apiserver.services.cache.model.Document;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * User: mikenimer
 * Date: 10/25/13
 */
@Component
public class InMemoryCacheProvider implements IDocumentCacheProvider
{

    // todo: replace with a better cache collection type
    private static Map<String, Object> cache = Collections.synchronizedMap(new LinkedHashMap<String, Object>());


    public InMemoryCacheProvider()
    {
        //loadTestData();
    }


    private void loadTestData()
    {
        try
        {
            String imgID = "8D981024-A297-4169-8603-E503CC38EEDA";
            File file = new File(  this.getClass().getClassLoader().getResource("sample.png").toURI()  );
            Document doc = new Document(file);
            doc.setId(imgID);
            doc.setContentType(MimeType.png);
            cache.put(imgID, doc);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public IDocument add(IDocument doc)
    {
        if( doc.getId() == null )
        {
            ((Document)doc).setId(UUID.randomUUID().toString());
        }
        cache.put(doc.getId(), doc);
        return doc;
    }


    @Override
    public Document get(String key)
    {
        return (Document)cache.get(key);
    }


    @Override
    public Document delete(String key)
    {
        return (Document)cache.remove(key);
    }
}
