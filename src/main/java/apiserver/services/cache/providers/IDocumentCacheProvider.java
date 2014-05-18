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

import apiserver.core.model.IDocument;

/**
 * User: mnimer
 * Date: 8/17/12
 *
 * A cache interface that could be implemented by a system like ehcache, or memcache - then exposed to applications through this interface.
 */
public interface IDocumentCacheProvider
{
    /**
     *
     * @param obj
     * @return  key
     */
    IDocument add(IDocument obj);

    /**
     * Get an item out of the cache, based on KEY returned from add() method
     * @param key
     * @return  object in cache or NULL
     */
    IDocument get(String key);

    /**
     * Get an item out of the cache, based on KEY returned from add() method
     * @param key
     * @return  object in cache or NULL
     */
    IDocument delete(String key);
}
