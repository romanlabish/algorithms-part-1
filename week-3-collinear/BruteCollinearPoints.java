/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final List<LineSegment> segments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();

        for (int j = 0; j < points.length; j++)
            if (points[j] == null)
                throw new IllegalArgumentException();

        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedPoints);
        for (int j = 1; j < points.length; j++)
            if (sortedPoints[j].compareTo(sortedPoints[j - 1]) == 0)
                throw new IllegalArgumentException();

        if (points.length < 4)
            return;

        List<Point> collinearPoints = new ArrayList<>();
        List<Point> collinearPointsAdded = new ArrayList<>();

        for (int i = 0; i < sortedPoints.length; i++) {
            for (int j = i + 1; j < sortedPoints.length; j++) {
                double ijSlope = sortedPoints[i].slopeTo(sortedPoints[j]);
                for (int k = j + 1; k < sortedPoints.length; k++) {
                    double ikSlope = sortedPoints[i].slopeTo(sortedPoints[k]);
                    if (Double.compare(ijSlope, ikSlope) != 0) continue;
                    for (int s = k + 1; s < sortedPoints.length; s++) {
                        double isSlope = sortedPoints[i].slopeTo(sortedPoints[s]);
                        if (Double.compare(ikSlope, isSlope) != 0) continue;

                        collinearPoints.add(sortedPoints[i]);
                        collinearPoints.add(sortedPoints[s]);
                    }
                }
            }
        }

        boolean exists;
        for (int i = 0; i < collinearPoints.size(); i += 2) {
            exists = false;
            for (int j = 0; j < collinearPointsAdded.size(); j += 2) {
                if (collinearPoints.get(i).compareTo(collinearPointsAdded.get(j)) == 0
                        && collinearPoints.get(i + 1).compareTo(collinearPointsAdded.get(j + 1))
                        == 0) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                collinearPointsAdded.add(collinearPoints.get(i));
                collinearPointsAdded.add(collinearPoints.get(i + 1));
                segments.add(new LineSegment(collinearPoints.get(i), collinearPoints.get(i + 1)));
            }
        }
    }    // finds all line segments containing 4 points

    public int numberOfSegments() {
        return segments.size();
    }        // the number of line segments

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }              // the line segments
}
