import java.util.*;

class Song {
    String name;
    int listens;
    long lastPlayedTime;

    public Song(String name, int listens, long lastPlayedTime) {
        this.name = name;
        this.listens = listens;
        this.lastPlayedTime = lastPlayedTime;
    }

    @Override
    public String toString() {
        return "Song{name='" + name + "', listens=" + listens + ", lastPlayedTime=" + lastPlayedTime + "}";
    }
}

public class SpotifySongSorter {

    public static void customSort(Song[] songs) {
        mergeSort(songs, 0, songs.length - 1);
    }

    private static void mergeSort(Song[] songs, int left, int right) {
        if (left >= right) return;

        int mid = left + (right - left) / 2;
        mergeSort(songs, left, mid);
        mergeSort(songs, mid + 1, right);

        merge(songs, left, mid, right);
    }

    private static void merge(Song[] songs, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Song[] leftArr = new Song[n1];
        Song[] rightArr = new Song[n2];

        for (int i = 0; i < n1; i++) leftArr[i] = songs[left + i];
        for (int i = 0; i < n2; i++) rightArr[i] = songs[mid + 1 + i];

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (compareSongs(leftArr[i], rightArr[j]) > 0) {
                songs[k++] = leftArr[i++];
            } else {
                songs[k++] = rightArr[j++];
            }
        }

        while (i < n1) songs[k++] = leftArr[i++];
        while (j < n2) songs[k++] = rightArr[j++];
    }

    private static int compareSongs(Song s1, Song s2) {
        if (s1.listens != s2.listens) {
            return Integer.compare(s2.listens, s1.listens); // More listens first
        }
        return Long.compare(s2.lastPlayedTime, s1.lastPlayedTime); // More recent first
    }

    public static void main(String[] args) {
        Song[] songs = {
            new Song("Song A", 50, 1632441000000L),
            new Song("Song B", 60, 1632442000000L),
            new Song("Song C", 50, 1632443000000L),
            new Song("Song D", 70, 1632440000000L)
        };

        System.out.println("Before sorting:");
        System.out.println(Arrays.toString(songs));

        customSort(songs);

        System.out.println("\nAfter sorting:");
        System.out.println(Arrays.toString(songs));
    }
}
