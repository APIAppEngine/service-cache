package apiserver.services.cache.gateway;

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
import apiserver.services.cache.gateway.jobs.DeleteDocumentJob;
import apiserver.services.cache.gateway.jobs.GetDocumentJob;
import apiserver.services.cache.gateway.jobs.UploadDocumentJob;

import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/19/13
 */
public interface CacheGateway
{
    Future<DocumentJob> addDocument(UploadDocumentJob args);

    Future<DocumentJob> deleteDocument(DeleteDocumentJob args);

    Future<DocumentJob> getDocument(GetDocumentJob args);
}
