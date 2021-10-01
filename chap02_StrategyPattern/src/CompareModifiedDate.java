public class CompareModifiedDate implements Comparable {
    @Override
    public int compareTo(Object o1, Object o2) {
        FileInfo pre = (FileInfo) o1;
        FileInfo post = (FileInfo) o2;

        return pre.modifiedDate.compareTo(post.modifiedDate);
    }
}
