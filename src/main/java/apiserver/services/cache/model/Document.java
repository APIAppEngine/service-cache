package apiserver.services.cache.model;

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
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

/**
 * Wrapper object for item stored in Persistence Layer and Cache Layers
 *
 * User: mnimer
 * Date: 9/28/12
 */
public class Document implements IDocument, Serializable
{
    private String id;
    private String fileName;
    private MimeType contentType;
    private Long size;
    private Object file;
    private byte[] fileBytes;
    // Metadata
    private String[] tags;


    /**
     * Create the document with a local file
     * @param file
     * @throws java.io.IOException
     */
    public Document(File file)  throws IOException
    {
        setFile(file);
    }


    /**
     * Create the document with an uploaded file
     * @param file
     * @throws java.io.IOException
     */
    public Document(MultipartFile file) throws IOException
    {
        setFile(file);
    }


    /**
     * Create the document with an uploaded file
     * @param file
     * @throws java.io.IOException
     */
    public Document(BufferedImage file) throws IOException
    {
        setFile(file);
    }


    public void setFile(Object file) throws IOException
    {
        if( file instanceof  File )
        {
            if( !((File)file).exists() || ((File)file).isDirectory() )
            {
                throw new IOException("Invalid File Reference");
            }

            fileName = ((File)file).getName();
            this.file = file;
            this.setFileName(fileName);
            this.contentType = MimeType.getMimeType(fileName);

            byte[] bytes = FileUtils.readFileToByteArray(((File)file));
            this.setFileBytes(bytes);
            this.setSize( new Integer(bytes.length).longValue() );
        }
        else if( file instanceof MultipartFile)
        {
            fileName = ((MultipartFile)file).getOriginalFilename();
            this.setContentType(MimeType.getMimeType(((MultipartFile) file).getContentType()));
            this.setFileName(((MultipartFile) file).getOriginalFilename());
            this.setFileBytes(((MultipartFile)file).getBytes());
            this.setSize( new Integer(this.getFileBytes().length).longValue() );
        }
        else if (file instanceof BufferedImage)
        {
            if( fileName == null )
            {
                fileName = UUID.randomUUID().toString();
            }

            // Convert buffered reader to byte array
            String _mime = this.getContentType().name();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write( (BufferedImage)file, _mime, byteArrayOutputStream );
            byteArrayOutputStream.flush();
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            this.setFileBytes(imageBytes);
        }
    }


    public String getId()
    {
        return id;
    }


    public void setId(String id)
    {
        this.id = id;
    }


    public String getFileName()
    {
        return fileName;
    }


    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }


    public MimeType getContentType()
    {
        return contentType;
    }


    public void setContentType(MimeType contentType)
    {
        this.contentType = contentType;
    }


    public Long getSize()
    {
        return size;
    }


    public void setSize(Long size)
    {
        this.size = size;
    }


    public byte[] getFileBytes()
    {
        return fileBytes;
    }


    public void setFileBytes(byte[] fileBytes)
    {
        this.fileBytes = fileBytes;
    }


    public String[] getTags()
    {
        return tags;
    }


    public void setTags(String[] tags)
    {
        this.tags = tags;
    }


    /**
     * Convert the internal file byte[] array back into a generic File to return.
     * @return
     * @throws java.io.IOException
     */
    public File getFile() throws IOException
    {
        if( this.file != null && this.file instanceof File && ((File)file).exists() )
        {
            //return (File)this.file;
        }


        FileOutputStream outputStream = null;

        String filePath = null;
        if( getId() != null )
        {
            filePath = System.getProperty("java.io.tmpdir") +"/" + getId() +"." +getFileName().split("\\.")[1];
        }else{
            filePath = System.getProperty("java.io.tmpdir") +"/" + getFileName();
        }

        file = new File(filePath);
        FileUtils.writeByteArrayToFile((File)file, getFileBytes());

        //((File)file).deleteOnExit();
        return (File)this.file;
    }

    /** Serializable example for BufferedImage
     *
     private void writeObject(java.io.ObjectOutputStream out)throws IOException{
     out.writeObject(name);
     ImageIO.write(image,"jpeg",ImageIO.createImageOutputStream(out));
     }

     private void readObject(java.io.ObjectInputStream in)throws IOException, ClassNotFoundException{
     name=(String)in.readObject();
     image=ImageIO.read(ImageIO.createImageInputStream(in));
     }

     //alternative
     private void writeObject(java.io.ObjectOutputStream out) throws IOException {
     out.writeObject(name);
     ImageWriter writer = (ImageWriter) ImageIO.getImageWritersBySuffix("jpg").next();
     writer.setOutput(ImageIO.createImageOutputStream(out));
     ImageWriteParam param = writer.getDefaultWriteParam();
     param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
     param.setCompressionQuality(0.85f);
     writer.write(null, new IIOImage(image, null, null), param);
     }
     */

}
