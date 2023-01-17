package utils.bot.DanielBotClasses;

import static utils.bot.DanielBotClasses.BitBoardBitManipulation.*;
import static utils.bot.DanielBotClasses.BitBoardConsts.*;

public class BitBoardAttacks {
	static long pawn_attacks[][] = new long[2][64];
	static long knight_attacks[] = new long[64];
	static long king_attacks[] = new long[64];
	
	static long maskPawnAttacks(int square, int side) {
		long attacks = 0L;
		long bitboard = 0L;
		
		bitboard |= (1L << square);
		
		if (side == 0) {
			if ((bitboard & not_a_file) != 0) attacks |= (bitboard >>> 9);
			if ((bitboard & not_h_file) != 0) attacks |= (bitboard >>> 7);
		} else {
			if ((bitboard & not_a_file) != 0) attacks |= (bitboard << 7);
			if ((bitboard & not_h_file) != 0) attacks |= (bitboard << 9);
		}
		
		return attacks;
	}
	
	static long maskKnightAttacks(int square) {
		long attacks = 0L;
		long bitboard = 0L;
		
		bitboard |= (1L << square);

	    if (((bitboard >>> 17) & not_h_file) != 0) attacks |= (bitboard >>> 17);
	    if (((bitboard >>> 15) & not_a_file) != 0) attacks |= (bitboard >>> 15);
	    if (((bitboard >>> 10) & not_hg_file) != 0) attacks |= (bitboard >>> 10);
	    if (((bitboard >>> 6) & not_ab_file) != 0) attacks |= (bitboard >>> 6);
	    if (((bitboard << 17) & not_a_file) != 0) attacks |= (bitboard << 17);
	    if (((bitboard << 15) & not_h_file) != 0) attacks |= (bitboard << 15);
	    if (((bitboard << 10) & not_ab_file) != 0) attacks |= (bitboard << 10);
	    if (((bitboard << 6) & not_hg_file) != 0) attacks |= (bitboard << 6);
		
	    return attacks;
	}
	
	static long maskKingAttacks(int square) {
		long attacks = 0L;
		long bitboard = 0L;
		
		bitboard |= (1L << square);
		
		if ((bitboard >>> 8) != 0) attacks |= (bitboard >>> 8);
	    if (((bitboard >>> 9) & not_h_file) != 0) attacks |= (bitboard >>> 9);
	    if (((bitboard >>> 7) & not_a_file) != 0) attacks |= (bitboard >>> 7);
	    if (((bitboard >>> 1) & not_h_file) != 0) attacks |= (bitboard >>> 1);
	    if ((bitboard << 8) != 0) attacks |= (bitboard << 8);
	    if (((bitboard << 9) & not_a_file) != 0) attacks |= (bitboard << 9);
	    if (((bitboard << 7) & not_h_file) != 0) attacks |= (bitboard << 7);
	    if (((bitboard << 1) & not_a_file) != 0) attacks |= (bitboard << 1);
	    
	    // return attack map
	    return attacks;
	}
	
	static long maskBishopAttacks(int square) {
		long attacks = 0L;
		
	    int r, f;
	    
	    int tr = square / 8;
	    int tf = square % 8;
	    
	    // mask relevant bishop occupancy bits
	    for (r = tr + 1, f = tf + 1; r <= 6 && f <= 6; r++, f++) attacks |= (1L << (r * 8 + f));
	    for (r = tr - 1, f = tf + 1; r >= 1 && f <= 6; r--, f++) attacks |= (1L << (r * 8 + f));
	    for (r = tr + 1, f = tf - 1; r <= 6 && f >= 1; r++, f--) attacks |= (1L << (r * 8 + f));
	    for (r = tr - 1, f = tf - 1; r >= 1 && f >= 1; r--, f--) attacks |= (1L << (r * 8 + f));
	    
	    // return attack map
	    return attacks;
	}
	
	static long maskRookAttacks(int square) {
		long attacks = 0L;
		
		int r, f;
		
	    int tr = square / 8;
	    int tf = square % 8;
	    
	    for (r = tr + 1; r <= 6; r++) attacks |= (1L << (r * 8 + tf));
	    for (r = tr - 1; r >= 1; r--) attacks |= (1L << (r * 8 + tf));
	    for (f = tf + 1; f <= 6; f++) attacks |= (1L << (tr * 8 + f));
	    for (f = tf - 1; f >= 1; f--) attacks |= (1L << (tr * 8 + f));
	    
	    // return attack map
	    return attacks;
	}
	
	static long bishopAttacksOnTheFly(int square, long block)
	{
	    long attacks = 0L;
	    
	    int r, f;
	    
	    int tr = square / 8;
	    int tf = square % 8;
	    
	    for (r = tr + 1, f = tf + 1; r <= 7 && f <= 7; r++, f++)
	    {
	        attacks |= (1L << (r * 8 + f));
	        if (((1L << (r * 8 + f)) & block) != 0) break;
	    }
	    
	    for (r = tr - 1, f = tf + 1; r >= 0 && f <= 7; r--, f++)
	    {
	        attacks |= (1L << (r * 8 + f));
	        if (((1L << (r * 8 + f)) & block) != 0) break;
	    }
	    
	    for (r = tr + 1, f = tf - 1; r <= 7 && f >= 0; r++, f--)
	    {
	        attacks |= (1L << (r * 8 + f));
	        if (((1L << (r * 8 + f)) & block) != 0) break;
	    }
	    
	    for (r = tr - 1, f = tf - 1; r >= 0 && f >= 0; r--, f--)
	    {
	        attacks |= (1L << (r * 8 + f));
	        if (((1L << (r * 8 + f)) & block) != 0) break;
	    }
	    
	    // return attack map
	    return attacks;
	}

	static long rookAttacksOnTheFly(int square, long block)
	{
	    long attacks = 0L;
	    
	    int r, f;
	    
	    int tr = square / 8;
	    int tf = square % 8;
	    
	    for (r = tr + 1; r <= 7; r++)
	    {
	        attacks |= (1L << (r * 8 + tf));
	        if (((1L << (r * 8 + tf)) & block) != 0) break;
	    }
	    
	    for (r = tr - 1; r >= 0; r--)
	    {
	        attacks |= (1L << (r * 8 + tf));
	        if (((1L << (r * 8 + tf)) & block) != 0) break;
	    }
	    
	    for (f = tf + 1; f <= 7; f++)
	    {
	        attacks |= (1L << (tr * 8 + f));
	        if (((1L << (tr * 8 + f)) & block) != 0) break;
	    }
	    
	    for (f = tf - 1; f >= 0; f--)
	    {
	        attacks |= (1L << (tr * 8 + f));
	        if (((1L << (tr * 8 + f)) & block) != 0) break;
	    }
	    
	    // return attack map
	    return attacks;
	}
	
	static void initLeapersAttacks() {
		for (int square = 0; square < 64; square++) {
			pawn_attacks[white][square] = maskPawnAttacks(square, white);
			pawn_attacks[black][square] = maskPawnAttacks(square, black);
			
			knight_attacks[square] = maskKnightAttacks(square);
			
			king_attacks[square] = maskKingAttacks(square);
		}
	}
	
	static long setOccupancy(int index, int bitsInMask, long attackMask) {
		long occupancy = 0L;
		
		for (int count = 0; count < bitsInMask; count++) {
			int square = getLS1BIndex(attackMask);
			
			attackMask = popBit(attackMask, square);
			
			if ((index & (1 << count)) != 0) {
				occupancy |= (1L << square);
			}
		}
		
		return occupancy;
	}
}