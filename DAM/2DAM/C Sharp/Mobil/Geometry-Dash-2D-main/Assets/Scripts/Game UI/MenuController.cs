using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class MenuController : MonoBehaviour
{
    public Sprite spriteAçık;
    public Sprite spriteKapalı;
    private bool isAçık = true;
    private Button button;

    private void Start()
    {
        button = GameObject.Find("Sound").GetComponent<Button>();
        LoadButtonState(); // Kaydedilen durumu yükle
    }

    public void ToggleButtonState()
    {
        isAçık = !isAçık;

        if (isAçık)
        {
            PlayerPrefs.SetInt("Music", 0);
            button.image.sprite = spriteAçık;
             PlayerPrefs.Save();
        }
        else
        {
            PlayerPrefs.SetInt("Music", 1);
            button.image.sprite = spriteKapalı;
             PlayerPrefs.Save();
        }

        SaveButtonState(); // Durumu kaydet
    }

    private void SaveButtonState()
    {
        PlayerPrefs.SetInt("ButtonState", isAçık ? 1 : 0);
        PlayerPrefs.Save();
    }

    private void LoadButtonState()
    {
        isAçık = PlayerPrefs.GetInt("ButtonState", 1) == 1;
        
        if (isAçık)
        {
            button.image.sprite = spriteAçık;
        }
        else
        {
            button.image.sprite = spriteKapalı;
        }
    }

    public void StartGame()
    {
        SceneManager.LoadScene("MainGame"); // Ana sahneye geçiş
    }

    public void Quit(){
        Application.Quit();
    }
}
