using UnityEngine;

public class MusicController : MonoBehaviour
{
    private AudioSource audioSource;

    private void Start()
    {
        
            audioSource = GetComponent<AudioSource>();
        int kayitliDeger = PlayerPrefs.GetInt("Music", 0);

        if(kayitliDeger==0){
            audioSource.Play();

        }
        if(kayitliDeger==1){
             audioSource.Stop();
        }
    }


}
