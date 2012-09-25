package de.jarnbjo.ogg;

import de.jarnbjo.ogg.EndOfOggStreamException;
import de.jarnbjo.ogg.OggPage;
import de.jarnbjo.ogg.OnDemandUrlStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public final class LogicalOggStreamImpl {

   private OnDemandUrlStream source;
   private ArrayList pageNumberMapping = new ArrayList();
   private int pageIndex;
   private OggPage currentPage;
   private int currentSegmentIndex;


   public LogicalOggStreamImpl(OnDemandUrlStream var1) {
      new ArrayList();
      this.pageIndex = 0;
      this.source = var1;
   }

   private synchronized OggPage getNextOggPage() {
      this.currentPage = this.source.getOggPage();
      return this.currentPage;
   }

   public final synchronized byte[] getNextOggPacket() {
	   try
	   {
		   ByteArrayOutputStream var1 = new ByteArrayOutputStream();
		   boolean var2 = false;
		   if(this.currentPage == null) {
			   this.currentPage = this.getNextOggPage();
		   }

		   int var4;
		   do {
			   OggPage var3 = this.currentPage;
			   if(this.currentSegmentIndex >= this.currentPage.segmentOffsets.length) {
				   this.currentSegmentIndex = 0;
				   var3 = this.currentPage;
				   if(this.currentPage.eos) {
					   throw new EndOfOggStreamException();
				   }

				   this.currentPage = this.getNextOggPage();
				   if(var1.size() == 0) {
					   var3 = this.currentPage;
					   if(this.currentPage.continued) {
						   var2 = false;

						   while(!var2) {
							   var3 = this.currentPage;
							   if(this.currentPage.segmentLengths[this.currentSegmentIndex++] != 255) {
								   var2 = true;
							   }

							   var3 = this.currentPage;
							   if(this.currentSegmentIndex > this.currentPage.segmentTable.length) {
								   OnDemandUrlStream var10001 = this.source;
								   ((Integer)this.pageNumberMapping.get(this.pageIndex++)).intValue();
								   this.currentPage = var10001.getOggPage();
							   }
						   }
					   }
				   }
			   }

			   var3 = this.currentPage;
			   if(this.currentPage.segmentLengths.length == 0) {
				   break;
			   }

			   var3 = this.currentPage;
			   var4 = this.currentPage.segmentLengths[this.currentSegmentIndex];
			   var3 = this.currentPage;
			   var3 = this.currentPage;
			   var1.write(this.currentPage.data, this.currentPage.segmentOffsets[this.currentSegmentIndex], var4);
			   ++this.currentSegmentIndex;
		   } while(var4 == 255);

		   return var1.toByteArray();
	   //} catch (EndOfOggStreamException e) {
	   } catch (Exception e) {
		   e.printStackTrace();
	   }

	   return null;
   }
}
