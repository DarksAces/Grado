using UnityEngine;
using System.Collections;
using UnityEngine.SceneManagement;
using TMPro;


public class GameManager : MonoBehaviour
{
    private const string AttemptKey = "Attempt";
    private static GameManager _instance;

    public GameObject Ground;
    //public GameObject upDownGround;
    public TMP_Text textMeshPro;

    public GameObject SecondZoneStick ;

    private bool isInSecondZone = false;

    public static GameManager Instance
    {
        get
        {
            if (_instance == null)
            {
                _instance = FindObjectOfType<GameManager>();
            }
            return _instance;
        }
    }

    private void Start()
    {
        InitializeAttempts();

    }

    public void Menu(){
        SceneManager.LoadScene("MainMenu");
    }
    private void InitializeAttempts()
    {
        int currentAttempt = PlayerPrefs.GetInt(AttemptKey, 0);
        SetAttempts(currentAttempt);
    }

    private void SetAttempts(int attempts)
    {
        PlayerPrefs.SetInt(AttemptKey, attempts);
        UpdateTextMesh(attempts);
    }

    private int score = 0;

    public void IncreaseScore() {
        score++;
        UpdateTextMesh(GetAttempts());
    }

    private void UpdateTextMesh(int attempts)
    {
        if (textMeshPro != null)
        {
            textMeshPro.fontSize = 35; // Más pequeño
            textMeshPro.text = ("Attempt " + attempts + " | Score: " + score);
        }
    }

    public void IncreaseAttempts()
    {
        int currentAttempts = GetAttempts();
        currentAttempts++;
        SetAttempts(currentAttempts);
    }

    public int GetAttempts()
    {
        return PlayerPrefs.GetInt(AttemptKey, 0);
    }

    public void EnterSecondZone()
    {
        isInSecondZone = true;
    }

    public void ExitSecondZone()
    {
        isInSecondZone = false;
    }

    public bool IsInSecondZone()
    {
        return isInSecondZone;
    }

    public void PlayerHitObstacle()
    {
        IncreaseAttempts();

            SceneManager.LoadScene("MainGame");

    }

    public void ExitGame()
    {
        Application.Quit();
    }

    public void RemoveGround(){
        Ground.SetActive(false);
     //   upDownGround.SetActive(true);
        SecondZoneStick.SetActive(true);
    }
}