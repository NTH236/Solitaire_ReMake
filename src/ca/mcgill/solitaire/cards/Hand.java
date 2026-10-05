package ca.mcgill.solitaire.cards;

import java.util.*;

public class Hand implements Comparable<Hand>, Iterable<Card> {
	private final List<Card> aCards = new ArrayList<>();

    public Hand(List<Card> pCards) {
        // Thiết lập tuyến phòng thủ: Bắt buộc đúng 5 quân bài
        if (pCards == null || pCards.size() != 5) {
            throw new IllegalArgumentException("Một tay bài Poker hợp lệ phải có chính xác 5 lá.");
        }
        aCards.addAll(pCards);
    }

    public PokerHandType getPokerHandType() {
        // Gom nhóm đếm số lần xuất hiện của từng Rank
        Map<Rank, Integer> rankCounts = new HashMap<>();
        Set<Suit> suits = new HashSet<>();

        for (Card card : aCards) {
            rankCounts.put(card.rank(), rankCounts.getOrDefault(card.rank(), 0) + 1);
            suits.add(card.suit());
        }

        boolean isFlush = (suits.size() == 1);
        boolean isStraight = checkStraight();

        // 1. Straight Flush
        if (isFlush && isStraight) return PokerHandType.STRAIGHT_FLUSH;

        // Phân tích tần suất xuất hiện của các bậc bài
        List<Integer> counts = new ArrayList<>(rankCounts.values());
        Collections.sort(counts, Collections.reverseOrder());

        // 2. Four of a Kind (Tứ quý)
        if (counts.get(0) == 4) return PokerHandType.FOUR_OF_A_KIND;

        // 3. Full House (Cù lũ: 3 lá cùng bậc + 2 lá cùng bậc)
        if (counts.get(0) == 3 && counts.get(1) == 2) return PokerHandType.FULL_HOUSE;

        // 4. Flush (Thùng)
        if (isFlush) return PokerHandType.FLUSH;

        // 5. Straight (Sảnh: chỉ xét Ace-low)
        if (isStraight) return PokerHandType.STRAIGHT;

        // 6. Three of a Kind (Xám cô)
        if (counts.get(0) == 3) return PokerHandType.THREE_OF_A_KIND;

        // 7. Two Pair (Hai đôi)
        if (counts.get(0) == 2 && counts.get(1) == 2) return PokerHandType.TWO_PAIR;

        // 8. One Pair (Một đôi)
        if (counts.get(0) == 2) return PokerHandType.ONE_PAIR;

        return PokerHandType.HIGH_CARD;
    }

    private boolean checkStraight() {
        List<Card> sortedCards = new ArrayList<>(aCards);
        // Sắp xếp bài tăng dần theo thứ tự ordinal của Rank
        sortedCards.sort(Comparator.comparingInt(c -> c.rank().ordinal()));

        for (int i = 0; i < sortedCards.size() - 1; i++) {
            int currentOrdinal = sortedCards.get(i).rank().ordinal();
            int nextOrdinal = sortedCards.get(i + 1).rank().ordinal();
            if (nextOrdinal != currentOrdinal + 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int compareTo(Hand pOther) {
        // Đơn giản hóa: Chỉ so sánh cấp bậc PokerHandType, bỏ qua giá trị phụ khi hòa
        return this.getPokerHandType().compareTo(pOther.getPokerHandType());
    }

    @Override
    public Iterator<Card> iterator() {
        return Collections.unmodifiableList(aCards).iterator();
    }
}
