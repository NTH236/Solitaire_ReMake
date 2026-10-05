package ca.mcgill.solitaire.cards;

import java.util.Arrays;

public class TestPoker {
	public static void main(String[] args) {
        // Khởi tạo tay bài 1: Cù lũ (Full House - 3 Át, 2 Già)
        Hand fullHouse = new Hand(Arrays.asList(
            Card.get(Rank.ACE, Suit.SPADES),
            Card.get(Rank.ACE, Suit.HEARTS),
            Card.get(Rank.ACE, Suit.CLUBS),
            Card.get(Rank.KING, Suit.DIAMONDS),
            Card.get(Rank.KING, Suit.SPADES)
        ));

        // Khởi tạo tay bài 2: Thùng (Flush - 5 lá cơ không liên tiếp)
        Hand flush = new Hand(Arrays.asList(
            Card.get(Rank.TWO, Suit.HEARTS),
            Card.get(Rank.FOUR, Suit.HEARTS),
            Card.get(Rank.SIX, Suit.HEARTS),
            Card.get(Rank.EIGHT, Suit.HEARTS),
            Card.get(Rank.JACK, Suit.HEARTS)
        ));

        System.out.println("Sức mạnh Tay bài 1: " + fullHouse.getPokerHandType());
        System.out.println("Sức mạnh Tay bài 2: " + flush.getPokerHandType());

        // Kích hoạt cơ chế đối kháng
        int result = fullHouse.compareTo(flush);
        
        System.out.println("---------------------------------");
        if (result > 0) {
            System.out.println("Phán quyết: Tay bài 1 (Cù Lũ) nghiền nát Tay bài 2.");
        } else if (result < 0) {
            System.out.println("Phán quyết: Tay bài 2 (Thùng) giành chiến thắng.");
        } else {
            System.out.println("Phán quyết: Cả hai tay bài hòa nhau.");
        }
    }
}
