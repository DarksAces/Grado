using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Obstacle : MonoBehaviour
{
    public bool isKillZone = true;

    private void Start()
    {
        // Añadimos el componente visual que genera el triángulo (pincho)
        if (gameObject.GetComponent<SpikeVisual>() == null) {
            gameObject.AddComponent<SpikeVisual>();
        }
    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        if (isKillZone && collision.CompareTag("Player"))
        {
            GameManager.Instance.PlayerHitObstacle();
        }
    }
}
