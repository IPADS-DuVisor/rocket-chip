// See LICENSE.Berkeley for license details.

package freechips.rocketchip.rocket.constants

import Chisel._
import freechips.rocketchip.util._

trait ScalarOpConstants {
  val SZ_BR = 3
  def BR_X    = BitPat("b???")
  def BR_EQ   = UInt(0, 3)
  def BR_NE   = UInt(1, 3)
  def BR_J    = UInt(2, 3)
  def BR_N    = UInt(3, 3)
  def BR_LT   = UInt(4, 3)
  def BR_GE   = UInt(5, 3)
  def BR_LTU  = UInt(6, 3)
  def BR_GEU  = UInt(7, 3)

  def A1_X    = BitPat("b??")
  def A1_ZERO = UInt(0, 2)
  def A1_RS1  = UInt(1, 2)
  def A1_PC   = UInt(2, 2)
  def A1_DEF  = UInt(3, 2)

  def IMM_X  = BitPat("b???")
  def IMM_S  = UInt(0, 3)
  def IMM_SB = UInt(1, 3)
  def IMM_U  = UInt(2, 3)
  def IMM_UJ = UInt(3, 3)
  def IMM_I  = UInt(4, 3)
  def IMM_Z  = UInt(5, 3)

  def A2_X    = BitPat("b??")
  def A2_ZERO = UInt(0, 2)
  def A2_SIZE = UInt(1, 2)
  def A2_RS2  = UInt(2, 2)
  def A2_IMM  = UInt(3, 2)

  def MMIO_TIME     = UInt(0, 3)
  def MMIO_VTIMECMP = UInt(4, 3)
  def MMIO_VTIMECTL = UInt(6, 3)
  def MMIO_VIPI0    = UInt(1, 3)
  def MMIO_VIPI1    = UInt(2, 3)
  def MMIO_VIPI2    = UInt(3, 3)
  def MMIO_VIPI3    = UInt(5, 3)
  def MMIO_VCPUID   = UInt(7, 3)

  def X = BitPat("b?")
  def N = BitPat("b0")
  def Y = BitPat("b1")

  val SZ_DW = 1
  def DW_X  = X
  def DW_32 = Bool(false)
  def DW_64 = Bool(true)
  def DW_XPR = DW_64
}

trait MemoryOpConstants {
  val NUM_XA_OPS = 9
  val M_SZ      = 5
  def M_X       = BitPat("b?????");
  def M_XRD     = UInt("b00000"); // int load
  def M_XWR     = UInt("b00001"); // int store
  def M_PFR     = UInt("b00010"); // prefetch with intent to read
  def M_PFW     = UInt("b00011"); // prefetch with intent to write
  def M_XA_SWAP = UInt("b00100");
  def M_FLUSH_ALL = UInt("b00101")  // flush all lines
  def M_XLR     = UInt("b00110");
  def M_XSC     = UInt("b00111");
  def M_XA_ADD  = UInt("b01000");
  def M_XA_XOR  = UInt("b01001");
  def M_XA_OR   = UInt("b01010");
  def M_XA_AND  = UInt("b01011");
  def M_XA_MIN  = UInt("b01100");
  def M_XA_MAX  = UInt("b01101");
  def M_XA_MINU = UInt("b01110");
  def M_XA_MAXU = UInt("b01111");
  def M_FLUSH   = UInt("b10000") // write back dirty data and cede R/W permissions
  def M_PWR     = UInt("b10001") // partial (masked) store
  def M_PRODUCE = UInt("b10010") // write back dirty data and cede W permissions
  def M_CLEAN   = UInt("b10011") // write back dirty data and retain R/W permissions
  def M_SFENCE  = UInt("b10100") // SFENCE.VMA
  def M_HFENCEV = UInt("b10101") // HFENCE.VVMA
  def M_HFENCEG = UInt("b10110") // HFENCE.GVMA
  def M_WOK     = UInt("b10111") // check write permissions but don't perform a write
  def M_HLVX    = UInt("b10000") // HLVX instruction

  def isAMOLogical(cmd: UInt) = cmd.isOneOf(M_XA_SWAP, M_XA_XOR, M_XA_OR, M_XA_AND)
  def isAMOArithmetic(cmd: UInt) = cmd.isOneOf(M_XA_ADD, M_XA_MIN, M_XA_MAX, M_XA_MINU, M_XA_MAXU)
  def isAMO(cmd: UInt) = isAMOLogical(cmd) || isAMOArithmetic(cmd)
  def isPrefetch(cmd: UInt) = cmd === M_PFR || cmd === M_PFW
  def isRead(cmd: UInt) = cmd.isOneOf(M_XRD, M_HLVX, M_XLR, M_XSC) || isAMO(cmd)
  def isWrite(cmd: UInt) = cmd === M_XWR || cmd === M_PWR || cmd === M_XSC || isAMO(cmd)
  def isWriteIntent(cmd: UInt) = isWrite(cmd) || cmd === M_PFW || cmd === M_XLR
}
