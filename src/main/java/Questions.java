import java.util.Random;

public class Questions {
    static final String[] questions = new String[]{
            "Worst things to say during sex",
            "Your favourite chat up line",
            "How to win favour with your boss",
            "How to calm down an angry customer",
            "I thought it was a ghost, but it was a...",
            "Which time period would you like to live in and why?",
            "My partner broke up with me because...",
            "A better name for a penis",
            "A better name for a vagina",
            "The Shrek films could have been massively improved by...",
            "Star Wars would have been better if it weren't for...",
            "If Harry Potter wasn't a wizard, he would have been...",
            "I'd love to have a job where...",
            "If your partner isn't listening to you, you should...",
            "The thing I hate most about work is...",
            "If you are what you eat, then I'm...",
            "It's cool and refreshing, it's...",
            "A new slogan for Apple should be...",
            "I've never been more relaxed than the time...",
            "My life was changed when...",
            "If I was a magically creature I would be...",
            "If you're tired of Bisto, then you're tired of...",
            "Nothing makes me happier than..."
    };
    public String random(){
        return questions[new Random().nextInt(questions.length)];
    }

}
