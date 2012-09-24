package de.jarnbjo.vorbis;

import de.jarnbjo.util.io.BitInputStream;
import de.jarnbjo.vorbis.Mode;
import de.jarnbjo.vorbis.Residue;
import de.jarnbjo.vorbis.SetupHeader;
import de.jarnbjo.vorbis.VorbisStream;

final class Residue0 extends Residue {

   protected Residue0(BitInputStream var1, SetupHeader var2) {
      super(var1, var2);
   }

   protected final void decodeResidue(VorbisStream var1, BitInputStream var2, Mode var3, boolean[] var4, float[][] var5) {
      throw new UnsupportedOperationException();
   }
}
