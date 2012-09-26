/*
 * $ProjectName$
 * $ProjectRevision$
 * -----------------------------------------------------------
 * $Id: VorbisFormatException.java,v 1.2 2003/03/16 01:11:12 jarnbjo Exp $
 * -----------------------------------------------------------
 *
 * $Author: jarnbjo $
 *
 * Description:
 *
 * Copyright 2002-2003 Tor-Einar Jarnbjo
 * -----------------------------------------------------------
 *
 * Change History
 * -----------------------------------------------------------
 * $Log: VorbisFormatException.java,v $
 * Revision 1.2  2003/03/16 01:11:12  jarnbjo
 * no message
 *
 *
 */
 
package de.jarnbjo.vorbis;

import java.io.IOException;

/**
 * Exception thrown when trying to read a corrupted Vorbis stream.
 */

public class VorbisFormatException extends IOException {

   public VorbisFormatException() {
      super();
   }

   public VorbisFormatException(String message) {
      super(message);
   }
}