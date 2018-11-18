import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final List<LineSegment> segments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();

        for (int j = 0; j < points.length; j++)
            if (points[j] == null)
                throw new IllegalArgumentException();

        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);
        for (int j = 1; j < points.length; j++)
            if (pointsCopy[j].compareTo(pointsCopy[j - 1]) == 0)
                throw new IllegalArgumentException();

        if (points.length < 4)
            return;

        Point[] sortedPoints = new Point[points.length - 1];

        for (int i = 0; i < pointsCopy.length; i++) {
            int count = 0;
            for (int j = 0; j < pointsCopy.length; j++) {
                if (i != j)
                    sortedPoints[count++] = pointsCopy[j];
            }

            Arrays.sort(sortedPoints);
            Arrays.sort(sortedPoints, pointsCopy[i].slopeOrder());

            int j = 0;
            int start = 0;
            int end = 0;
            double prevSlope = pointsCopy[i].slopeTo(sortedPoints[j++]);
            while (true) {
                if (j == sortedPoints.length) {
                    if (end - start > 1 && end < sortedPoints.length) {

                        if (pointsCopy[i].compareTo(sortedPoints[start]) < 0)
                            segments.add(new LineSegment(pointsCopy[i], sortedPoints[end]));
                    }
                    break;
                }
                double currentSlope = pointsCopy[i].slopeTo(sortedPoints[j++]);
                if (Double.compare(prevSlope, currentSlope) == 0) {
                    end++;
                }
                else {
                    if (end - start > 1) {
                        if (pointsCopy[i].compareTo(sortedPoints[start]) < 0)
                            segments.add(new LineSegment(pointsCopy[i], sortedPoints[end]));
                    }
                    end = j - 1;
                    start = j - 1;
                    prevSlope = currentSlope;
                }
            }
        }
    }    // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return segments.size();
    }       // the number of line segments

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }                // the line segments
}
