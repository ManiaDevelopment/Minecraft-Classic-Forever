/*
 * $ProjectName$
 * $ProjectRevision$
 * -----------------------------------------------------------
 * $Id: EndOfOggStreamException.java,v 1.1 2003/03/03 21:02:20 jarnbjo Exp $
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
 * $Log: EndOfOggStreamException.java,v $
 * Revision 1.1  2003/03/03 21:02:20  jarnbjo
 * no message
 *
 */

 package de.jarnbjo.ogg;

import java.io.IOException;

/**
 * Exception thrown when reaching the end of an Ogg stream
 */

public class EndOfOggStreamException extends IOException {

   public EndOfOggStreamException() {
   }
}