package apiserver.services.cache.gateway.jobs;


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

import apiserver.services.cache.DocumentJob;
import apiserver.services.cache.model.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * User: mikenimer
 * Date: 7/19/13
 */
public class UploadDocumentJob extends DocumentJob
{

    /**
     * create the object with the Multipart file that was uploaded
     * @param multipartFile uploaded file
     * @throws java.io.IOException
     */
    public UploadDocumentJob(MultipartFile multipartFile) throws IOException
    {
        Document doc = new Document(multipartFile);
        this.setDocument(doc);
    }


    /**
     * create the object with the local file, useful during unit testing.
     * @param file local file reference
     * @throws java.io.IOException
     */
    public UploadDocumentJob(File file) throws IOException
    {
        Document doc = new Document(file);
        this.setDocument(doc);
    }


    /**
     * set extra metadata
     * @param tags list of tags to describe the file
     */
    public void setTags(String[] tags)
    {
        this.getDocument().setTags(tags);
    }

}
