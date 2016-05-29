package com.github.kirishin.coincidencedetector;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 開始日時と終了日時から成る「期間」を表すクラス。
 * <p>
 * このクラスのオブジェクトは不変です。
 *
 * @author kirishin
 *
 * @since 1.0.0
 */
public class TimePeriod implements Comparable<TimePeriod> {

	/**
	 * 開始日時。
	 */
	private final LocalDateTime start;
	/**
	 * 終了日時。
	 */
	private final LocalDateTime end;

	/**
	 * 期間の両端を表す2つの日時を指定して、{@code TimePeriod}のインスタンスを取得します。
	 * <p>
	 * 2つの日時のうち、過去の方が開始日時として、未来の方が終了日時として使用されます。
	 *
	 * @param date1
	 *            日時、null以外
	 * @param date2
	 *            日時、null以外
	 * @return {@code TimePeriod}オブジェクト。null以外
	 * @throws NullPointerException
	 *             引数のどちらかがnullである場合
	 */
	public static TimePeriod of(LocalDateTime date1, LocalDateTime date2) {
		Objects.requireNonNull(date1, "date1 must not be null");
		Objects.requireNonNull(date2, "date2 must not be null");
		if (date1.isAfter(date2)) {
			return new TimePeriod(date2, date1);
		} else {
			return new TimePeriod(date1, date2);
		}
	}

	/**
	 * 起点日時と期間の長さを指定して、{@code TimePeriod}のインスタンスを取得します。
	 * <p>
	 * 起点日時と、起点日時に期間長を加算した日時のうち、過去の方が開始日時として、未来の方が終了日時として使用されます。
	 *
	 * @param base
	 *            起点日時、null以外
	 * @param duration
	 *            期間の長さ、null以外
	 * @return {@code TimePeriod}オブジェクト。null以外
	 * @throws NullPointerException
	 *             引数のどちらかがnullである場合
	 */
	public static TimePeriod of(LocalDateTime base, Duration duration) {

		Objects.requireNonNull(base, "start must not be null");
		Objects.requireNonNull(duration, "duration must not be null");
		LocalDateTime added = base.plus(duration);
		return of(base, added);
	}

	/**
	 * 開始日時と終了日時を指定して、{@code TimePeriod}のインスタンスを取得します。
	 *
	 * @param start
	 *            開始日時
	 * @param end
	 *            終了日時
	 */
	private TimePeriod(LocalDateTime start, LocalDateTime end) {

		this.start = start;
		this.end = end;
	}

	/**
	 * この期間の開始日時を取得します。
	 *
	 * @return 開始日時
	 */
	public LocalDateTime getStartTime() {
		return start;
	}

	/**
	 * この期間の終了日時を取得します。
	 *
	 * @return 終了日時
	 */
	public LocalDateTime getEndTime() {
		return end;
	}

	/**
	 * このオブジェクトのハッシュコードを取得します。
	 *
	 * @return ハッシュコード
	 */
	@Override
	public int hashCode() {
		return Objects.hash(start, end);
	}

	/**
	 * この期間オブジェクトが他のオブジェクトと等しいかどうかを確認します。
	 * <p>
	 * 対象のオブジェクトが{@code TimePeriod} 型で、開始日時と終了日時がそれぞれ等しい場合のみ、trueを返します。
	 *
	 * @param o
	 *            対象オブジェクト
	 * @return 対象オブジェクトと同じ期間を表す場合はtrue、それ以外はfalse
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (getClass() != o.getClass()) {
			return false;
		}
		TimePeriod other = (TimePeriod) o;
		return this.start.equals(other.start) && this.end.equals(other.end);
	}

	/**
	 * この期間を{@code String}として出力します。
	 * <p>
	 * 以下に示すフォーマットで、開始日時と終了日時がそれぞれISO-8601形式で出力されます。
	 * {@code 'from:'uuuu-MM-dd'T'HH:mm:ss' to:'uuuu-MM-dd'T'HH:mm:ss }
	 * </p>
	 *
	 * @return この期間の文字列表現、null以外
	 */
	@Override
	public String toString() {
		return "from:" + start.toString() + " to:" + end.toString();
	}

	/**
	 * この期間を他の期間オブジェクトと比較します。
	 * <p>
	 * この比較は、「範囲の開始日時の早い順、開始日時が等しい場合は終了日時の早い順」の順序付けを定義します。 {@link Comparable}
	 * の定義通り、{@link #equals}と一致します。
	 *
	 * @param o
	 *            対象オブジェクト、null以外
	 * @return コンパレータ値。小さい場合は負、大きい場合は正
	 */
	@Override
	public int compareTo(TimePeriod o) {
		int cmp = this.start.compareTo(o.start);
		if (cmp == 0) {
			cmp = this.end.compareTo(o.end);
		}
		return cmp;
	}
}
