interface Playable {
    String play();
    String play(int fromSecond);
    String pause();
}

public abstract class MediaFile {
    private static int counter = 1000;
    private final String fileId;

    public MediaFile() {
        counter++;
        this.fileId = "MF-" + counter;
    }

    public String getFileId() {
        return fileId;
    }

    public abstract String getFormatInfo();

    public static void launchAll(Playable[] items) {
        if (items != null) {
            for (Playable item : items) {
                if (item != null) {
                    System.out.println(item.play());
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 4: Universal Media Launcher ===");

        AudioFile a = new AudioFile("Morning Jazz");
        System.out.println(a.play());
        System.out.println(a.play(30));
        System.out.println(a.getFormatInfo());

        Podcast p = new Podcast("Tech Talk", 12);
        System.out.println(p.play());

        Playable ref = a; // Upcasting AudioFile to Playable
        System.out.println("\n--- Launching All ---");
        launchAll(new Playable[]{ ref, p });
    }
}

class AudioFile extends MediaFile implements Playable {
    private final String title;

    public AudioFile(String title) {
        super();
        this.title = title;
    }

    @Override
    public String getFormatInfo() {
        return "Audio file, ID: " + getFileId();
    }

    @Override
    public String play() {
        return "Playing audio: " + title;
    }

    @Override
    public String play(int fromSecond) {
        int mins = fromSecond / 60;
        int secs = fromSecond % 60;
        return "Playing audio: " + title + " from " + String.format("%d:%02d", mins, secs);
    }

    @Override
    public String pause() {
        return "Paused audio: " + title;
    }
}

/*
 * DESIGN JUSTIFICATION:
 * AudioFile IS-A MediaFile because it represents a static local media asset with file metadata and a system ID.
 * Podcast is CAN-DO Playable only because it is a live network stream rather than a physical file asset — 
 * forcing Podcast under MediaFile would pollute it with file attributes it has no use for.
 */
class Podcast implements Playable {
    private final String showName;
    private final int episodeNumber;

    public Podcast(String showName, int episodeNumber) {
        this.showName = showName;
        this.episodeNumber = episodeNumber;
    }

    @Override
    public String play() {
        return "Streaming episode " + episodeNumber + " of " + showName;
    }

    @Override
    public String play(int fromSecond) {
        return "Streaming episode " + episodeNumber + " of " + showName + " from " + fromSecond + "s";
    }

    @Override
    public String pause() {
        return "Paused podcast: " + showName;
    }
}