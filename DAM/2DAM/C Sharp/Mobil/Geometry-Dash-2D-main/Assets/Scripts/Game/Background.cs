using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Background : MonoBehaviour
{

    public float speed = 5f; // kayma hızı
    public float speedGround=5f;
    public Transform[] backgroundSpriteObjects; // Sprite nesnelerini içeren dizi
    public Transform[] groundSpriteObjects; // Sprite nesnelerini içeren dizi

    private float spriteBackWidth;         // Sprite genişliği
    private float spriteGroundWidth;         // Sprite genişliği



    private void Update()
    {
        foreach (Transform spriteObject in backgroundSpriteObjects)
        {
            spriteObject.position += Vector3.left * (speed * Time.deltaTime);

            // Nesne ekranın solundan kaybolduğunda sağa geri konumlandırma
            if (spriteObject.position.x < -10f)
            {
                Vector3 newPos = spriteObject.position;
                newPos.x += 61.85f;
                spriteObject.position = newPos;
            }
        }

        foreach (Transform groundObject in groundSpriteObjects)
        {
            groundObject.position += Vector3.left * (speedGround * Time.deltaTime);

            // Nesne ekranın solundan kaybolduğunda sağa geri konumlandırma
            if (groundObject.position.x < -20f)
            {
                Vector3 newPos = groundObject.position;
                newPos.x += 60f;
                groundObject.position = newPos;
            }
        }

        
    }
}
