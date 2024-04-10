public class Solution {
    int source;
    int destination;
    int distance;

    public Solution(int source, int destination, int distance) {
        this.destination = destination;
        this.distance = distance;
        this.source = source;
    }

    public int getSource() {
        return source;
    }

    public int getDestination() {
        return destination;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Solution solution = (Solution) obj;

        return this.destination == solution.destination && this.source == solution.source
                && this.distance == solution.distance;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + destination;
        result = 31 * result + source;
        result = 31 * result + distance;
        return result;
    }
}
