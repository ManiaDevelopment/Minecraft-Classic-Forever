package de.jarnbjo.ogg;

import de.jarnbjo.ogg.LogicalOggStreamImpl;
import de.jarnbjo.ogg.OggPage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedList;

public final class OnDemandUrlStream {

   private URLConnection source;
   private InputStream sourceStream;
   private int position;
   public HashMap logicalStreams;
   private OggPage firstPage;


   public OnDemandUrlStream(URL var1) {
	   try
	   {
		   new Object();
		   new LinkedList();
		   this.position = 0;
		   this.logicalStreams = new HashMap();
		   this.source = var1.openConnection();
		   this.sourceStream = this.source.getInputStream();
		   this.source.getContentLength();
		   this.firstPage = OggPage.create(this.sourceStream);
		   this.position += this.firstPage.getTotalLength();
		   LogicalOggStreamImpl var3 = new LogicalOggStreamImpl(this);
		   OggPage var2;
		   this.logicalStreams.put(new Integer((var2 = this.firstPage).streamSerialNumber), var3);
	   } catch (IOException e) {
		   e.printStackTrace();
	   }
   }

   public final OggPage getOggPage() {
      OggPage var1;
      if(this.firstPage != null) {
         var1 = this.firstPage;
         this.firstPage = null;
         return var1;
      } else {
         var1 = OggPage.create(this.sourceStream);
         this.position += var1.getTotalLength();
         return var1;
      }
   }
}
